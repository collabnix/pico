
# coding: utf-8

# In[12]:


import os
import sys
import time
import cv2
import json
import decimal


import pytz
from pytz import timezone
import datetime


from kafka import KafkaProducer
from kafka.errors import KafkaError
import base64 

topic = "testpico"
brokers = ["35.189.130.4:9092"]

imagesfolder = 'images/'

camera_data = {'camera_id':"1","position":"frontspace","image_bytes":"123"}

producer = KafkaProducer(bootstrap_servers=brokers,
                            value_serializer=lambda v: json.dumps(v).encode('utf-8'))

def convert_ts(ts, config):
    '''Converts a timestamp to the configured timezone. Returns a localized datetime object.'''
    #lambda_tz = timezone('US/Pacific')
    tz = timezone(config['timezone'])
    utc = pytz.utc
    
    utc_dt = utc.localize(datetime.datetime.utcfromtimestamp(ts))

    localized_dt = utc_dt.astimezone(tz)

    return localized_dt


camera = cv2.VideoCapture(0)

framecount = 0
try:
    
#     for root, dirs, files in os.walk(imagesfolder):
#         for filename in files:
#             print(filename)

    while(True):

        success, frame = camera.read()
        utc_dt = pytz.utc.localize(datetime.datetime.now())
        now_ts_utc = (utc_dt - datetime.datetime(1970, 1, 1, tzinfo=pytz.utc)).total_seconds()

#         frame = cv2.imread(imagesfolder+filename, 0)

        retval, buffer = cv2.imencode(".jpg", frame)

        camera_data['image_bytes'] = base64.b64encode(buffer).decode('utf-8')

        camera_data['frame_count'] = str(framecount)

        camera_data['capture_time'] = str(now_ts_utc)

        producer.send(topic, camera_data)

        framecount = framecount + 1

        time.sleep(0.02)
            
except Exception as e:
        print((e))
        print("\nExiting.")
        producer.close()
        sys.exit(1)
        
producer.close()
      
        
        
        
        


# In[ ]:




