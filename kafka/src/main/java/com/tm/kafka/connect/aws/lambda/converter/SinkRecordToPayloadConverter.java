package com.tm.kafka.connect.aws.lambda.converter;

import org.apache.kafka.connect.errors.RetriableException;
import org.apache.kafka.connect.sink.SinkRecord;

import java.util.function.Function;

public interface SinkRecordToPayloadConverter extends Function<SinkRecord, String> {
  String convert(final SinkRecord record) throws Exception;

  default String apply(final SinkRecord record) {
    try {
      return convert(record);
    } catch (final Exception e) {
      throw new RetriableException("Payload converter " + getClass().getName() + " failed to convert '" + record.toString(), e);
    }
  }
}
