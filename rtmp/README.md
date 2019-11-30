# RTMP + Nginx for Video Streaming using Docker on Jetson Nano

- Real-Time Messaging Protocol (RTMP) is an open source protocol owned by Adobe that’s designed to stream audio and video by maintaining low latency connections.
- It is a TCP-based protocol designed to maintain low-latency connections for audio and video streaming. 
- It is a protocol for streaming audio, video, and data over the Internet.
- To increase the amount of data that can be smoothly transmitted, streams are split into smaller fragments called packets. 
- RTMP also defines several virtual channels that work independently of each other for packets to be delivered on. 
- This means that video and audio are delivered on separate channels simultaneously.
- Clients use a handshake to form a connection with an RTMP server which then allows users to stream video and audio.
- TMP live streaming generally requires a media server and a content delivery network, but by leveraging StackPath EdgeCompute you can remove the need for a CDN and drastically reduce latency and costs.


## How to run RTMP inside Docker Container

```
docker run -d -p 1935:1935 --name nginx-rtmp ajeetraina/nginx-rtmp-arm:latest
```

If you want to build the Docker Image from Dockerfile, follow the below steps:

```
git clone https://github.com/collabnix/nginx-rtmp-docker
cd nginx-rtmp-docker/
docker build -t ajeetraina/nginx-rtmp-arm .
```

## Testing RTMP with OBS Studio and VLC

- Open OBS Studio
- Click the "Settings" button
- Go to the "Stream" section
- In "Stream Type" select "Custom Streaming Server"
- In the "URL" enter the rtmp://<ip_of_host>/live replacing <ip_of_host> with the IP of the host in which the container is running. For example: rtmp://192.168.0.30/live
- In the "Stream key" use a "key" that will be used later in the client URL to display that specific stream. For example: test
- Click the "OK" button
- In the section "Sources" click de "Add" button (+) and select a source (for example "Screen Capture") and configure it as you need
- Click the "Start Streaming" button
- Open a VLC player (it also works in Raspberry Pi using omxplayer)
- Click in the "Media" menu
- Click in "Open Network Stream"
- Enter the URL from above as rtmp://<ip_of_host>/live/<key> replacing <ip_of_host> with the IP of the host in which the container is running and <key> with the key you created in OBS Studio. For example: rtmp://192.168.0.30/live/test
- Click "Play"
- Now VLC should start playing whatever you are transmitting from OBS Studio
