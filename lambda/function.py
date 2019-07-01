import json
from kafka import KafkaConsumer

def lambda_handler(event, context):

    consumer = KafkaConsumer('aws-lambda-topic', bootstrap_servers='35.189.130.4:9092')

    for message in consumer:
        return { 'statusCode': 200, 'body': json.dumps(str(message.value))}
~                                                                             
