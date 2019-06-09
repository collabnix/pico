# Testing the Kafka Cluster by pushing a sample video frame

## Pre-requisite

- Apache Kafka Cluster Setup - Follow [this](https://github.com/collabnix/pico/blob/master/kafka/README.md)
- Create an instance outside Kafka Cluster with Docker binaries installed

## Setting up Environment for Producer Script

## Pulling the Container

```
docker pull https://github.com/ajeetraina/opencv4-python3
```

## Running the container exposing port 5000

```
docker run -itd -p 5000:5000 ajeetraina/opencv4-python3 bash
docker attach <container-id>
```

## Cloning the Repository

```
git clone https://github.com/collabnix/pico
cd pico/kafka/
```

## Modify the producer

Two entries needed to be changed:
- topic name(which you must have supplied during the initial Kafka cluster configuration)
- bootstrapper server IP pointing to your Kafka Broker

```
import sys
import time
import cv2
# from picamera.array import PiRGBArray
# from picamera import PiCamera
from kafka import KafkaProducer
from kafka.errors import KafkaError

topic = "testpico"

def publish_video(video_file):
    """
    Publish given video file to a specified Kafka topic. 
    Kafka Server is expected to be running on the localhost. Not partitioned.
    
    :param video_file: path to video file <string>
    """
    # Start up producer
    producer = KafkaProducer(bootstrap_servers='10.140.0.2:9092')
  ```
  
  ```
  def publish_camera():
    """
    Publish camera video stream to specified Kafka topic.
    Kafka Server is expected to be running on the localhost. Not partitioned.
    """

    # Start up producer
    producer = KafkaProducer(bootstrap_servers='10.140.0.2:9092')
  
  ```
  
  ## Downloading a sample video 
  
  Download a sample video and rename it as Countdown1.mp4. Place it here in the same directory where producer and consumer resides.
  
  ## Executing the Script
  
  ```
  python producer.py
  ```
  
  ## Setting up Environment for Consumer Script
  
  Open up consumer script and modify the two important items:
  
  - Topic Name: testpico
  - Bootstrap Server: <IP>:9093
    
  Executing the Script
  
  ```
  python consumer.py
  ```
  
  ## Verifying the Video Streaming
  
  By now you can browse through http://<Instance-IP>:5000 to see video streaming.



