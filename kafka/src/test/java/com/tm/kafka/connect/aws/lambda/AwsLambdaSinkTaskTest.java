package com.tm.kafka.connect.aws.lambda;

import com.amazonaws.services.lambda.AbstractAWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.record.TimestampType;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTaskContext;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.tm.kafka.connect.aws.lambda.AwsLambdaSinkConnectorConfig.FUNCTION_NAME_CONFIG;
import static com.tm.kafka.connect.aws.lambda.AwsLambdaSinkConnectorConfig.REGION_CONFIG;
import static org.apache.kafka.connect.data.Schema.STRING_SCHEMA;
import static org.junit.Assert.assertEquals;

public class AwsLambdaSinkTaskTest {

  private static final String TOPIC = "aws-lambda-topic";
  private static final int PARTITION = 12;
  private static final int PARTITION2 = 13;

  private static final TopicPartition TOPIC_PARTITION = new TopicPartition(TOPIC, PARTITION);
  private static final TopicPartition TOPIC_PARTITION2 = new TopicPartition(TOPIC, PARTITION2);
  private static final String FUNCTION_NAME = "kafka-aws-lambda-test";
  private static final String REGION = "us-west-2";

  @Test
  public void test() {
    Map<String, String> props = new HashMap<String, String>() {{
      put(FUNCTION_NAME_CONFIG, FUNCTION_NAME);
      put(REGION_CONFIG, REGION);
    }};

    Set<TopicPartition> assignment = new HashSet<>();
    assignment.add(TOPIC_PARTITION);
    assignment.add(TOPIC_PARTITION2);
    MockSinkTaskContext context = new MockSinkTaskContext(assignment);

    AwsLambdaSinkTask task = new AwsLambdaSinkTask();


    Collection<SinkRecord> records = new ArrayList<>();
    int partition = 1;
    String key = "key1";

    Schema valueSchema = SchemaBuilder.struct()
      .name("com.example.Person")
      .field("name", STRING_SCHEMA)
      .field("age", Schema.INT32_SCHEMA)
      .build();

    String bobbyMcGee = "Bobby McGee";
    int value21 = 21;

    Struct value = new Struct(valueSchema)
      .put("name", bobbyMcGee)
      .put("age", value21);

    long offset = 100;
    long timestamp = 200L;
    SinkRecord sinkRecord = new SinkRecord(
      TOPIC,
      partition,
      STRING_SCHEMA,
      key,
      valueSchema,
      value,
      offset,
      timestamp,
      TimestampType.CREATE_TIME);
    records.add(sinkRecord);

    String payload = "{\"schema\":" +
      "{\"type\":\"struct\"," +
      "\"fields\":[" +
      "{\"type\":\"string\",\"optional\":false,\"field\":\"name\"}," +
      "{\"type\":\"int32\",\"optional\":false,\"field\":\"age\"}" +
      "]," +
      "\"optional\":false," +
      "\"name\":\"com.example.Person\"}," +
      "\"payload\":{\"name\":\"Bobby McGee\",\"age\":21}}";

    task.setClient(new AbstractAWSLambda() {
      @Override
      public InvokeResult invoke(final InvokeRequest request) {
        assertEquals(FUNCTION_NAME, request.getFunctionName());
        assertEquals(payload, new String(request.getPayload().array()));
        return null;
      }
    });

    task.initialize(context);
    task.start(props);
    task.put(records);
  }

  protected static class MockSinkTaskContext implements SinkTaskContext {

    private final Map<TopicPartition, Long> offsets;
    private long timeoutMs;
    private Set<TopicPartition> assignment;

    MockSinkTaskContext(Set<TopicPartition> assignment) {
      this.offsets = new HashMap<>();
      this.timeoutMs = -1L;
      this.assignment = assignment;
    }

    @Override
    public Map<String, String> configs() {
      return null;
    }

    @Override
    public void offset(Map<TopicPartition, Long> offsets) {
      this.offsets.putAll(offsets);
    }

    @Override
    public void offset(TopicPartition tp, long offset) {
      offsets.put(tp, offset);
    }

    public Map<TopicPartition, Long> offsets() {
      return offsets;
    }

    @Override
    public void timeout(long timeoutMs) {
      this.timeoutMs = timeoutMs;
    }

    public long timeout() {
      return timeoutMs;
    }

    @Override
    public Set<TopicPartition> assignment() {
      return assignment;
    }

    public void setAssignment(Set<TopicPartition> nextAssignment) {
      assignment = nextAssignment;
    }

    @Override
    public void pause(TopicPartition... partitions) {
    }

    @Override
    public void resume(TopicPartition... partitions) {
    }

    @Override
    public void requestCommit() {
    }
  }
}
