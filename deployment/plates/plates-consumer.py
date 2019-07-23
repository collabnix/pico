
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

consumer = KafkaConsumer(
    topic, 
    bootstrap_servers=brokers,
    value_deserializer=lambda m: json.loads(m.decode('utf-8')))


# In[18]:


session = boto3.session.Session(aws_access_key_id='XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX',
                                aws_secret_access_key='XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX',
                                region_name='us-west-2')


for msg in consumer:
    
    img_bytes = base64.b64decode(msg.value['image_bytes'])

    rekog_client = session.client('rekognition')
    response = rekog_client.detect_text(Image={'Bytes':img_bytes})

    textDetections=response['TextDetections']

    for text in textDetections:
                print ('Detected text:' + text['DetectedText'])
                print ('Confidence: ' + "{:.2f}".format(text['Confidence']) + "%")
                print ('Id: {}'.format(text['Id']))
                if 'ParentId' in text:
                    print ('Parent Id: {}'.format(text['ParentId']))
                print ('Type:' + text['Type'])
                
    print("#"*50)


# In[ ]:




