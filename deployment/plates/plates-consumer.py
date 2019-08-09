
# coding: utf-8

# In[17]:


import datetime
from kafka import KafkaConsumer
import boto3
import json
import base64 

# Fire up the Kafka Consumer
topic = "testpico"
brokers = ["35.189.130.4:9092"]

# Initialising Kafka consumer(Lambda) with topic 
consumer = KafkaConsumer(
    topic, 
    bootstrap_servers=brokers,
    value_deserializer=lambda m: json.loads(m.decode('utf-8')))


# In[18]:

# Initialising AWS session using Secrey Keys
session = boto3.session.Session(aws_access_key_id='XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX',
                                aws_secret_access_key='XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX',
                                region_name='us-west-2')

# Reading every message in the consumer topic(queue) followed by decoding at the producer
for msg in consumer:
    
    img_bytes = base64.b64decode(msg.value['image_bytes'])

    
 # Initializing the AWS Rekognition System
    rekog_client = session.client('rekognition')
 
 # Sending the Image byte array to the AWS Rekognition System to detect the text in the image

    response = rekog_client.detect_text(Image={'Bytes':img_bytes})  
    
 # Capturing the text detections from the AWS Rekognition System response
 

    textDetections=response['TextDetections']

    for text in textDetections:
                print ('Detected text:' + text['DetectedText'])
                print ('Confidence: ' + "{:.2f}".format(text['Confidence']) + "%")
                
    print("#"*50)


# In[ ]:




