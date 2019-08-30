
# coding: utf-8

# In[ ]:
# Boto3 is the Amazon Web Services (AWS) Software Development Kit (SDK) for Python, which allows Python developers to write software that makes use of services like Amazon S3 and Amazon EC2. 
# OpenCV is a library of programming functions mainly aimed at real-time computer vision


import boto3
import json
import cv2
import decimal
from copy import deepcopy

#from __future__ import print_function
import base64
import datetime
import time
import decimal
import uuid
import json
import boto3
import pytz
from pytz import timezone
from copy import deepcopy

from PIL import Image, ImageDraw, ExifTags, ImageColor, ImageFont

import datetime
from kafka import KafkaConsumer, KafkaProducer
import boto3
import json
import base64
import io

# Fire up the Kafka Consumer
topic = "image-pool"
brokers = ["35.221.215.135:9092"]

consumer = KafkaConsumer(
    topic, 
    bootstrap_servers=brokers,
    value_deserializer=lambda m: json.loads(m.decode('utf-8')))


# In[18]:

producer = KafkaProducer(bootstrap_servers=brokers,
                            value_serializer=lambda v: json.dumps(v).encode('utf-8'))


AWS_ACCESS_KEY_ID = 'XXXXXXXXXXXXXXXXXXXXXXXXXX'
AWS_SECRET_ACCESS_KEY = 'XXXXXXXXXXXXXXXXXXXXXXXX'


session = boto3.session.Session(aws_access_key_id = AWS_ACCESS_KEY_ID,
                                aws_secret_access_key = AWS_SECRET_ACCESS_KEY,
                                region_name='us-west-2')


def load_config():
    '''Load configuration from file.'''
    with open('image-processor.json', 'r') as conf_file:
        conf_json = conf_file.read()
        return json.loads(conf_json)

#Load config
config = load_config()

def start_processor():
    
    while True:
        
#         raw_frame_messages = consumer.poll(timeout_ms=10, max_records=10)
        raw_frame_messages = consumer.poll()
        
        for topic_partition, msgs in raw_frame_messages.items():
            for msg in msgs:

                camera_data = {}

                img_bytes = base64.b64decode(msg.value['image_bytes'])

                camera_topic = "camera"+str(msg.value['camera_id'])

                stream = io.BytesIO(img_bytes)
                image=Image.open(stream)

                imgWidth, imgHeight = image.size  
                draw = ImageDraw.Draw(image) 

                rekog_client = session.client('rekognition')
                rekog_max_labels = config["rekog_max_labels"]
                rekog_min_conf = float(config["rekog_min_conf"])

                label_watch_list = config["label_watch_list"]
                label_watch_min_conf = float(config["label_watch_min_conf"])
                label_watch_phone_num = config.get("label_watch_phone_num", "")
                label_watch_sns_topic_arn = config.get("label_watch_sns_topic_arn", "")

                rekog_response = rekog_client.detect_labels(
                        Image={
                            'Bytes': img_bytes
                        },
                        MaxLabels=rekog_max_labels,
                        MinConfidence=rekog_min_conf
                    )

                boxes = []
                objects = []
                confidence = []

                for label in rekog_response['Labels']:

                        for instance in label['Instances']:

                            if(instance['BoundingBox']['Top'] > 0):

                                print("Found a Box of {}".format(label['Name']))

                                top = imgHeight * instance['BoundingBox']['Top']
                                left = imgWidth * instance['BoundingBox']['Left']
                                width = imgWidth * instance['BoundingBox']['Width']
                                height = imgHeight * instance['BoundingBox']['Height']

                                boxes.append([top,left,width,height])

                                objects.append(label['Name'])

                                confidence.append(label['Confidence'])  


                for i, box in enumerate(boxes):

                    top = box[0]
                    left = box[1]
                    width = box[2]
                    height = box[3]


                    points = (
                                (left,top),
                                (left + width, top),
                                (left + width, top + height),
                                (left , top + height),
                                (left, top)

                            )

                    font = ImageFont.truetype("arial.ttf", 25)
                    draw.line(points, fill='#00d400', width=3)

                    label = str(objects[i])+":"+str(confidence[i])
                    color = 'rgb(255,255,0)' # white color
                    draw.text((left, top - 25), label, fill=color,font=font)


                    imgByteArr = io.BytesIO()
                    image.save(imgByteArr, format=image.format)
                    imgByteArr = imgByteArr.getvalue()


                    camera_data['image_bytes'] = base64.b64encode(imgByteArr).decode('utf-8')

        #             print(camera_topic)

                    producer.send(camera_topic,camera_data)
        

if __name__ == "__main__":
    start_processor()


# In[ ]:




