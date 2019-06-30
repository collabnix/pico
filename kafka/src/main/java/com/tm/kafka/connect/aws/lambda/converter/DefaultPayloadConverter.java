package com.tm.kafka.connect.aws.lambda.converter;

import com.google.gson.Gson;
import org.apache.kafka.connect.sink.SinkRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPayloadConverter implements SinkRecordToPayloadConverter {
  private Logger log = LoggerFactory.getLogger(DefaultPayloadConverter.class);
  private Gson gson = new Gson();

  public String convert(SinkRecord record) {
    String payload = gson.toJson(record);
    log.trace("P: {}", payload);
    return payload;
  }
}
