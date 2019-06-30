package com.tm.kafka.connect.aws.lambda;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.tm.kafka.connect.aws.lambda.converter.JsonPayloadConverter;
import com.tm.kafka.connect.aws.lambda.converter.SinkRecordToPayloadConverter;
import org.apache.kafka.common.Configurable;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.utils.Utils;
import org.apache.kafka.connect.errors.ConnectException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.apache.kafka.common.config.ConfigDef.NO_DEFAULT_VALUE;


public class AwsLambdaSinkConnectorConfig extends AbstractConfig {

  static final String REGION_CONFIG = "aws.region";
  private static final String REGION_DOC_CONFIG = "The AWS region.";
  private static final String REGION_DISPLAY_CONFIG = "AWS region";

  private static final String CREDENTIALS_PROVIDER_CLASS_CONFIG = "aws.credentials.provider.class";
  private static final Class<? extends AWSCredentialsProvider> CREDENTIALS_PROVIDER_CLASS_DEFAULT =
    DefaultAWSCredentialsProviderChain.class;
  private static final String CREDENTIALS_PROVIDER_DOC_CONFIG =
    "Credentials provider or provider chain to use for authentication to AWS. By default "
      + "the connector uses 'DefaultAWSCredentialsProviderChain'.";
  private static final String CREDENTIALS_PROVIDER_DISPLAY_CONFIG = "AWS Credentials Provider Class";

  /**
   * The properties that begin with this prefix will be used to configure a class, specified by
   * {@code s3.credentials.provider.class} if it implements {@link Configurable}.
   */
  public static final String CREDENTIALS_PROVIDER_CONFIG_PREFIX =
    CREDENTIALS_PROVIDER_CLASS_CONFIG.substring(
      0,
      CREDENTIALS_PROVIDER_CLASS_CONFIG.lastIndexOf(".") + 1
    );

  static final String FUNCTION_NAME_CONFIG = "aws.function.name";
  private static final String FUNCTION_NAME_DOC = "The AWS Lambda function name.";
  private static final String FUNCTION_NAME_DISPLAY = "AWS Lambda function Name";

  private static final String RETRY_BACKOFF_CONFIG = "retry.backoff.ms";
  private static final String RETRY_BACKOFF_DOC =
    "The retry backoff in milliseconds. This config is used to notify Kafka connect to retry "
      + "delivering a message batch or performing recovery in case of transient exceptions.";
  private static final long RETRY_BACKOFF_DEFAULT = 5000L;
  private static final String RETRY_BACKOFF_DISPLAY = "Retry Backoff (ms)";

  private static final String INVOCATION_TYPE_CONFIG = "aws.lambda.invocation.type";
  private static final String INVOCATION_TYPE_DEFAULT = "RequestResponse";
  private static final String INVOCATION_TYPE_DOC_CONFIG = "AWS Lambda function invocation type.";
  private static final String INVOCATION_TYPE_DISPLAY_CONFIG = "Invocation type";

  private static final String PAYLOAD_CONVERTER_CONFIG = "aws.lambda.payload.converter.class";
  private static final Class<? extends SinkRecordToPayloadConverter> PAYLOAD_CONVERTER_DEFAULT =
    JsonPayloadConverter.class;
  private static final String PAYLOAD_CONVERTER_DOC_CONFIG =
    "Class to be used to convert Kafka messages from SinkRecord to Aws Lambda input";
  private static final String PAYLOAD_CONVERTER_DISPLAY_CONFIG = "Payload converter class";

  private final SinkRecordToPayloadConverter sinkRecordToPayloadConverter;
  private final InvokeRequest invokeRequest;

  @SuppressWarnings("unchecked")
  private AwsLambdaSinkConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
    super(config, parsedConfig);
    try {
      sinkRecordToPayloadConverter = ((Class<? extends SinkRecordToPayloadConverter>)
        getClass(PAYLOAD_CONVERTER_CONFIG)).getDeclaredConstructor().newInstance();
    } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
      throw new ConnectException("Invalid class for: " + PAYLOAD_CONVERTER_CONFIG, e);
    }
    invokeRequest = new InvokeRequest()
      .withFunctionName(getAwsFunctionName())
      .withInvocationType(getAwsLambdaInvocationType());
  }

  AwsLambdaSinkConnectorConfig(Map<String, String> parsedConfig) {
    this(conf(), parsedConfig);
  }

  public static ConfigDef conf() {
    String group = "AWS";
    int orderInGroup = 0;
    return new ConfigDef()
      .define(REGION_CONFIG,
        Type.STRING,
        NO_DEFAULT_VALUE,
        new RegionValidator(),
        Importance.HIGH,
        REGION_DOC_CONFIG,
        group,
        ++orderInGroup,
        ConfigDef.Width.SHORT,
        REGION_DISPLAY_CONFIG,
        new RegionRecommender())

      .define(CREDENTIALS_PROVIDER_CLASS_CONFIG,
        Type.CLASS,
        CREDENTIALS_PROVIDER_CLASS_DEFAULT,
        new CredentialsProviderValidator(),
        Importance.HIGH,
        CREDENTIALS_PROVIDER_DOC_CONFIG,
        group,
        ++orderInGroup,
        ConfigDef.Width.MEDIUM,
        CREDENTIALS_PROVIDER_DISPLAY_CONFIG)

      .define(FUNCTION_NAME_CONFIG,
        Type.STRING,
        NO_DEFAULT_VALUE,
        Importance.HIGH,
        FUNCTION_NAME_DOC,
        group,
        ++orderInGroup,
        ConfigDef.Width.SHORT,
        FUNCTION_NAME_DISPLAY)

      .define(RETRY_BACKOFF_CONFIG,
        Type.LONG,
        RETRY_BACKOFF_DEFAULT,
        Importance.LOW,
        RETRY_BACKOFF_DOC,
        group,
        ++orderInGroup,
        ConfigDef.Width.NONE,
        RETRY_BACKOFF_DISPLAY)

      .define(INVOCATION_TYPE_CONFIG,
        Type.STRING,
        INVOCATION_TYPE_DEFAULT,
        new InvocationTypeValidator(),
        Importance.LOW,
        INVOCATION_TYPE_DOC_CONFIG,
        group,
        ++orderInGroup,
        ConfigDef.Width.SHORT,
        INVOCATION_TYPE_DISPLAY_CONFIG,
        new InvocationTypeRecommender())

      .define(PAYLOAD_CONVERTER_CONFIG,
        Type.CLASS,
        PAYLOAD_CONVERTER_DEFAULT,
        new PayloadConverterValidator(),
        Importance.LOW,
        PAYLOAD_CONVERTER_DOC_CONFIG,
        group,
        ++orderInGroup,
        ConfigDef.Width.SHORT,
        PAYLOAD_CONVERTER_DISPLAY_CONFIG,
        new PayloadConverterRecommender())
      ;
  }

  public String getAwsRegion() {
    return this.getString(REGION_CONFIG);
  }

  @SuppressWarnings("unchecked")
  public AWSCredentialsProvider getAwsCredentialsProvider() {
    try {
      AWSCredentialsProvider awsCredentialsProvider = ((Class<? extends AWSCredentialsProvider>)
        getClass(CREDENTIALS_PROVIDER_CLASS_CONFIG)).getDeclaredConstructor().newInstance();
      if (awsCredentialsProvider instanceof Configurable) {
        Map<String, Object> configs = originalsWithPrefix(CREDENTIALS_PROVIDER_CONFIG_PREFIX);
        configs.remove(CREDENTIALS_PROVIDER_CLASS_CONFIG.substring(CREDENTIALS_PROVIDER_CONFIG_PREFIX.length()));
        ((Configurable) awsCredentialsProvider).configure(configs);
      }
      return awsCredentialsProvider;
    } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
      throw new ConnectException("Invalid class for: " + CREDENTIALS_PROVIDER_CLASS_CONFIG, e);
    }
  }

  public String getAwsFunctionName() {
    return this.getString(FUNCTION_NAME_CONFIG);
  }

  public Long getRetryBackoff() {
    return this.getLong(RETRY_BACKOFF_CONFIG);
  }

  private InvocationType getAwsLambdaInvocationType() {
    return InvocationType.fromValue(this.getString(INVOCATION_TYPE_CONFIG));
  }

  public SinkRecordToPayloadConverter getPayloadConverter() {
    return sinkRecordToPayloadConverter;
  }

  private static class RegionRecommender implements ConfigDef.Recommender {
    @Override
    public List<Object> validValues(String name, Map<String, Object> connectorConfigs) {
      return new ArrayList<>(RegionUtils.getRegions());
    }

    @Override
    public boolean visible(String name, Map<String, Object> connectorConfigs) {
      return true;
    }
  }

  private static class RegionValidator implements ConfigDef.Validator {
    @Override
    public void ensureValid(String name, Object region) {
      String regionStr = ((String) region).toLowerCase().trim();
      if (RegionUtils.getRegion(regionStr) == null) {
        throw new ConfigException(name, region, "Value must be one of: " + Utils.join(RegionUtils.getRegions(), ", "));
      }
    }

    @Override
    public String toString() {
      return "[" + Utils.join(RegionUtils.getRegions(), ", ") + "]";
    }
  }

  private static class CredentialsProviderValidator implements ConfigDef.Validator {
    @Override
    public void ensureValid(String name, Object provider) {
      if (provider instanceof Class && AWSCredentialsProvider.class.isAssignableFrom((Class<?>) provider)) {
        return;
      }
      throw new ConfigException(name, provider, "Class must extend: " + AWSCredentialsProvider.class);
    }

    @Override
    public String toString() {
      return "Any class implementing: " + AWSCredentialsProvider.class;
    }
  }

  private static class InvocationTypeRecommender implements ConfigDef.Recommender {
    @Override
    public List<Object> validValues(String name, Map<String, Object> connectorConfigs) {
      return Arrays.asList(InvocationType.values());
    }

    @Override
    public boolean visible(String name, Map<String, Object> connectorConfigs) {
      return true;
    }
  }

  private static class InvocationTypeValidator implements ConfigDef.Validator {
    @Override
    public void ensureValid(String name, Object invocationType) {
      try {
        InvocationType.fromValue(((String) invocationType).trim());
      } catch (Exception e) {
        throw new ConfigException(name, invocationType, "Value must be one of: " +
          Utils.join(InvocationType.values(), ", "));
      }
    }

    @Override
    public String toString() {
      return "[" + Utils.join(InvocationType.values(), ", ") + "]";
    }
  }

  private static class PayloadConverterRecommender implements ConfigDef.Recommender {
    @Override
    public List<Object> validValues(String name, Map<String, Object> connectorConfigs) {
      return Collections.singletonList(JsonPayloadConverter.class);
    }

    @Override
    public boolean visible(String name, Map<String, Object> connectorConfigs) {
      return true;
    }
  }

  private static class PayloadConverterValidator implements ConfigDef.Validator {
    @Override
    public void ensureValid(String name, Object provider) {
      if (provider instanceof Class && SinkRecordToPayloadConverter.class.isAssignableFrom((Class<?>) provider)) {
        return;
      }
      throw new ConfigException(name, provider, "Class must extend: " + SinkRecordToPayloadConverter.class);
    }

    @Override
    public String toString() {
      return "Any class implementing: " + SinkRecordToPayloadConverter.class;
    }
  }

  private static ConfigDef getConfig() {
    Map<String, ConfigDef.ConfigKey> everything = new HashMap<>(conf().configKeys());
    ConfigDef visible = new ConfigDef();
    for (ConfigDef.ConfigKey key : everything.values()) {
      visible.define(key);
    }
    return visible;
  }

  public Function<String, InvokeRequest> getInvokeRequestWithPayload() {
    return invokeRequest::withPayload;
  }

  public static void main(String[] args) {
    System.out.println(VersionUtil.getVersion());
    System.out.println(getConfig().toEnrichedRst());
  }

}
