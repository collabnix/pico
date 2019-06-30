package com.tm.kafka.connect.aws.lambda;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import org.apache.kafka.common.Configurable;

import java.util.Map;

public class ConfigurationAWSCredentialsProvider implements AWSCredentialsProvider, Configurable {

  private static final String AWS_ACCESS_KEY_ID_CONFIG = "aws.access.key.id";
  private static final String AWS_SECRET_ACCESS_KEY_CONFIG = "aws.secret.access.key";

  private AWSCredentials awsCredentials;

  @Override
  public AWSCredentials getCredentials() {
    return awsCredentials;
  }

  @Override
  public void refresh() {

  }

  @Override
  public void configure(final Map<String, ?> configs) {
    awsCredentials = new AWSCredentials() {
      private final String key = (String) configs.get(AWS_ACCESS_KEY_ID_CONFIG);
      private final String secret = (String) configs.get(AWS_SECRET_ACCESS_KEY_CONFIG);

      @Override
      public String getAWSAccessKeyId() {
        return key;
      }

      @Override
      public String getAWSSecretKey() {
        return secret;
      }
    };
  }
}
