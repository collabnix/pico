package com.tm.kafka.connect.aws.lambda;

import org.junit.Test;

public class AwsLambdaSinkConnectorConfigTest {
  @Test
  public void doc() {
    System.out.println(AwsLambdaSinkConnectorConfig.conf().toRst());
  }
}
