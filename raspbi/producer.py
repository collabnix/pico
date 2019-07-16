
# coding: utf-8

# In[26]:


import sys
import time
import cv2
import json
# from picamera.array import PiRGBArray
# from picamera import PiCamera
from kafka import KafkaProducer
from kafka.errors import KafkaError

import base64

topic = "testpico"
brokers = ["35.189.130.4:9092"]

# framerate = 


def publish_camera():
    """
    Publish camera video stream to specified Kafka topic.
    Kafka Server is expected to be running on the localhost. Not partitioned.
    """

    # Start up producer
    
#     producer = KafkaProducer(bootstrap_servers=brokers)
    producer = KafkaProducer(bootstrap_servers=brokers,
                            value_serializer=lambda v: json.dumps(v).encode('utf-8'))

    
    camera_data = {'camera-id':"1","position":"frontspace","image_bytes":"123","frame":"0"}
    
    camera = cv2.VideoCapture(0)
    
    i = 0
    
    try:
        while(True):
            success, frame = camera.read()
        
            ret, buffer = cv2.imencode('.jpg', frame)
            
            camera_data['image_bytes'] = str(buffer.tobytes())
            camera_data['frame'] = str(i)
#             producer.send(topic,buffer.tobytes())
            
            producer.send(topic, camera_data)
            
            i = i + 1
            
            # Choppier stream, reduced load on processor
            time.sleep(5)
            
    except Exception as e:
        print((e))
        print("\nExiting.")
        sys.exit(1)

    
    camera.release()
#     producer.close()


    
if __name__ == "__main__":
    publish_camera()


# In[24]:





# In[26]:





# In[32]:





# In[31]:





# In[21]:





# In[ ]:



