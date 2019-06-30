package com.tm.kafka.connect.aws.lambda.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.json.JsonConverter;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.connect.sink.SinkRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Collections.emptyMap;

public class JsonPayloadConverter implements SinkRecordToPayloadConverter {
  private Logger log = LoggerFactory.getLogger(JsonPayloadConverter.class);
  private ObjectMapper objectMapper = new ObjectMapper();
  private JsonConverter jsonConverter = new JsonConverter();
  private JsonDeserializer jsonDeserializer = new JsonDeserializer();

  public JsonPayloadConverter() {
    jsonConverter.configure(emptyMap(), false);
    jsonDeserializer.configure(emptyMap(), false);
  }

  public String convert(SinkRecord record) throws JsonProcessingException {
    String topic = record.topic();
    Schema schema = record.valueSchema();
    Object value = record.value();

    String payload = objectMapper.writeValueAsString(
      jsonDeserializer.deserialize(topic,
        jsonConverter.fromConnectData(topic, schema, value)));

    if (log.isTraceEnabled()) {
      log.trace("P: {}", payload);
    }

    return payload;
  }
}
