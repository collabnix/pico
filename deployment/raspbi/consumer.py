import datetime
from flask import Flask, Response, render_template
from kafka import KafkaConsumer

# Fire up the Kafka Consumer
topic = "testpico"
brokers = ["35.189.130.4:9092"]

consumer = KafkaConsumer(
    topic, 
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
        get_video_stream(id), 
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
        get_video_stream(id), 
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
        get_video_stream(id), 
        mimetype='multipart/x-mixed-replace; boundary=frame')

def get_video_stream(id):
    """
    Here is where we recieve streamed images from the Kafka Server and convert 
    them to a Flask-readable format.
    """
    for msg in consumer:
        if str(msg.value['camera_id']) == str(id):
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpg\r\n\r\n' + base64.b64decode(msg.value['image_bytes']) + b'\r\n\r\n')

if __name__ == "__main__":
    app.run(host='0.0.0.0', debug=True)
