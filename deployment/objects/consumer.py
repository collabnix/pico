
# coding: utf-8

# In[ ]:


import datetime
from flask import Flask, Response, render_template
from kafka import KafkaConsumer
import json
import base64

# Fire up the Kafka Consumer
camera_topic_1 = "camera1"
camera_topic_2 = "camera2"
camera_topic_3 = "camera3"
brokers = ["35.221.213.182:9092"]

camera1 = KafkaConsumer(
    camera_topic_1, 
    bootstrap_servers=brokers,
    value_deserializer=lambda m: json.loads(m.decode('utf-8')))

camera2 = KafkaConsumer(
    camera_topic_2, 
    bootstrap_servers=brokers,
    value_deserializer=lambda m: json.loads(m.decode('utf-8')))


camera3 = KafkaConsumer(
    camera_topic_3, 
    bootstrap_servers=brokers,
    value_deserializer=lambda m: json.loads(m.decode('utf-8')))


# Set the consumer in a Flask App
app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/camera_1', methods=['GET'])
def camera_1():
    id=5
    """
    This is the heart of our video display. Notice we set the mimetype to 
    multipart/x-mixed-replace. This tells Flask to replace any old images with 
    new values streaming through the pipeline.
    """
    return Response(
        getCamera1(), 
        mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/camera_2', methods=['GET'])
def camera_2():
    id=6
    """
    This is the heart of our video display. Notice we set the mimetype to 
    multipart/x-mixed-replace. This tells Flask to replace any old images with 
    new values streaming through the pipeline.
    """
    return Response(
        getCamera2(), 
        mimetype='multipart/x-mixed-replace; boundary=frame')


@app.route('/camera_3', methods=['GET'])
def camera_3():
    id=8
    """
    This is the heart of our video display. Notice we set the mimetype to 
    multipart/x-mixed-replace. This tells Flask to replace any old images with 
    new values streaming through the pipeline.
    """
    return Response(
        getCamera3(), 
        mimetype='multipart/x-mixed-replace; boundary=frame')

def getCamera1():
    """
    Here is where we recieve streamed images from the Kafka Server and convert 
    them to a Flask-readable format.
    """
    for msg in camera1:
        yield (b'--frame\r\n'
               b'Content-Type: image/jpg\r\n\r\n' + base64.b64decode(msg.value['image_bytes']) + b'\r\n\r\n')

def getCamera2():
    """
    Here is where we recieve streamed images from the Kafka Server and convert 
    them to a Flask-readable format.
    """
    for msg in camera2:
        yield (b'--frame\r\n'
               b'Content-Type: image/jpg\r\n\r\n' + base64.b64decode(msg.value['image_bytes']) + b'\r\n\r\n')
            
            
def getCamera3():
    """
    Here is where we recieve streamed images from the Kafka Server and convert 
    them to a Flask-readable format.
    """
    for msg in camera3:
        yield (b'--frame\r\n'
              b'Content-Type: image/jpg\r\n\r\n' + base64.b64decode(msg.value['image_bytes']) + b'\r\n\r\n')           
            
if __name__ == "__main__":
    app.run(host='0.0.0.0', debug=True)

