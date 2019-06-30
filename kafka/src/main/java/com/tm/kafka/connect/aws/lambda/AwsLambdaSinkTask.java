package com.tm.kafka.connect.aws.lambda;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AwsLambdaSinkTask extends SinkTask {
  private static Logger log = LoggerFactory.getLogger(AwsLambdaSinkTask.class);

  private AwsLambdaSinkConnectorConfig connectorConfig;
  private AWSLambda client;

  @Override
  public void start(Map<String, String> map) {
    connectorConfig = new AwsLambdaSinkConnectorConfig(map);
    context.timeout(connectorConfig.getRetryBackoff());
    if (client == null) {
      setClient(AWSLambdaAsyncClientBuilder.standard()
        .withRegion(connectorConfig.getAwsRegion())
        .withCredentials(connectorConfig.getAwsCredentialsProvider())
        .build());
    }
  }

  void setClient(AWSLambda client) {
    this.client = client;
  }

  @Override
  public void stop() {
    log.debug("Stopping sink task, setting client to null");
    client = null;
  }

  @Override
  public void put(Collection<SinkRecord> collection) {
    loggingWrapper(collection.stream()
      .map(connectorConfig.getPayloadConverter())
      .map(connectorConfig.getInvokeRequestWithPayload()))
      .forEach(client::invoke);

    if (log.isDebugEnabled()) {
      log.debug("Read {} records from Kafka", collection.size());
    }
  }

  private Stream<InvokeRequest> loggingWrapper(final Stream<InvokeRequest> stream) {
    return getLogFunction()
      .map(stream::peek) // if there is a function, stream to logging
      .orElse(stream); // or else just return the stream as is
  }

  private Optional<Consumer<InvokeRequest>> getLogFunction() {
    if (!log.isDebugEnabled()) {
      return Optional.empty();
    }
    if (!log.isTraceEnabled()) {
      return Optional.of(x -> log.debug("Calling " + connectorConfig.getAwsFunctionName()));
    }
    return Optional.of(x -> log.trace("Calling " + connectorConfig.getAwsFunctionName(),
      UTF_8.decode(x.getPayload()).toString()));
  }

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }
}
