# Running Yolo inside Docker Container running on Jetson Nano

## Pre-requisite:

- Jetson Nano running Docker 19.03
- Jetson Nano has two power mode, 5W and 10W. Set the powermode of the Jetson Nano to 5W by running the below CLI:

```
sudo nvpmodel -m 1
```

 - Connect webcam using USB port
 
 
## Setup a swap partition

In order to reduce memory pressure (and crashes), it is a good idea to setup a 6GB swap partition. (Nano has only 4GB of RAM)

```
git clone https://github.com/collabnix/installSwapfile
cd installSwapfile
chmod 777 installSwapfile.sh
```
```
./installSwapfile.sh
```

Reboot the Jetson nano.

## Verify your if your USB Camera is connected

```
ls /dev/video*
```

Output should be: /dev/video0

# Running the scripts

```
wget -N https://raw.githubusercontent.com/opendatacam/opendatacam/v2.1.0/docker/install-opendatacam.sh
chmod 777 install-opendatacam.sh
./install-opendatacam.sh --platform nano
```

```


jetson@worker1:~$ sudo docker ps
CONTAINER ID        IMAGE                                 COMMAND                  CREATED             STATUS              PORTS                                                                               NAMES
aae5117a06c6        opendatacam/opendatacam:v2.1.0-nano   "/bin/sh -c ./docker…"   15 minutes ago      Up 5 minutes        0.0.0.0:8070->8070/tcp, 0.0.0.0:8080->8080/tcp, 0.0.0.0:8090->8090/tcp, 27017/tcp   heuristic_bardeen
jetson@worker1:~$ sudo docker logs -f aae
2020-01-05T10:24:01.840+0000 I STORAGE  [main] Max cache overflow file size custom option: 0
2020-01-05T10:24:01.845+0000 I CONTROL  [main] Automatically disabling TLS 1.0, to force-enable TLS 1.0 specify --sslDisabledProtocols 'none'
2020-01-05T10:24:01.853+0000 I CONTROL  [initandlisten] MongoDB starting : pid=8 port=27017 dbpath=/data/db 64-bit host=aae5117a06c6
2020-01-05T10:24:01.853+0000 I CONTROL  [initandlisten] db version v4.0.12
2020-01-05T10:24:01.853+0000 I CONTROL  [initandlisten] git version: 5776e3cbf9e7afe86e6b29e22520ffb6766e95d4
2020-01-05T10:24:01.853+0000 I CONTROL  [initandlisten] OpenSSL version: OpenSSL 1.0.2n  7 Dec 2017
2020-01-05T10:24:01.853+0000 I CONTROL  [initandlisten] allocator: tcmalloc
2020-01-05T10:24:01.853+0000 I CONTROL  [initandlisten] modules: none
2020-01-05T10:24:01.853+0000 I CONTROL  [initandlisten] build environment:
2020-01-05T10:24:01.853+0000 I CONTROL  [initandlisten]     distmod: ubuntu1604
2020-01-05T10:24:01.853+0000 I CONTROL  [initandlisten]     distarch: aarch64
2020-01-05T10:24:01.853+0000 I CONTROL  [initandlisten]     target_arch: aarch64
2020-01-05T10:24:01.853+0000 I CONTROL  [initandlisten] options: {}
2020-01-05T10:24:01.854+0000 I STORAGE  [initandlisten]
2020-01-05T10:24:01.854+0000 I STORAGE  [initandlisten] ** WARNING: Using the XFS filesystem is strongly recommended with the WiredTiger storage engine
2020-01-05T10:24:01.854+0000 I STORAGE  [initandlisten] **          See http://dochub.mongodb.org/core/prodnotes-filesystem
2020-01-05T10:24:01.854+0000 I STORAGE  [initandlisten] wiredtiger_open config: create,cache_size=1470M,cache_overflow=(file_max=0M),session_max=20000,eviction=(threads_min=4,threads_max=4),config_base=false,statistics=(fast),log=(enabled=true,archive=true,path=journal,compressor=snappy),file_manager=(close_idle_time=100000),statistics_log=(wait=0),verbose=(recovery_progress),
2020-01-05T10:24:03.612+0000 I STORAGE  [initandlisten] WiredTiger message [1578219843:612093][8:0x7fb6246440], txn-recover: Set global recovery timestamp: 0
2020-01-05T10:24:03.669+0000 I RECOVERY [initandlisten] WiredTiger recoveryTimestamp. Ts: Timestamp(0, 0)
2020-01-05T10:24:03.730+0000 I CONTROL  [initandlisten]
2020-01-05T10:24:03.730+0000 I CONTROL  [initandlisten] ** WARNING: Access control is not enabled for the database.
2020-01-05T10:24:03.730+0000 I CONTROL  [initandlisten] **          Read and write access to data and configuration is unrestricted.
2020-01-05T10:24:03.730+0000 I CONTROL  [initandlisten] ** WARNING: You are running this process as the root user, which is not recommended.
2020-01-05T10:24:03.730+0000 I CONTROL  [initandlisten]
2020-01-05T10:24:03.731+0000 I CONTROL  [initandlisten] ** WARNING: This server is bound to localhost.
2020-01-05T10:24:03.731+0000 I CONTROL  [initandlisten] **          Remote systems will be unable to connect to this server.
2020-01-05T10:24:03.731+0000 I CONTROL  [initandlisten] **          Start the server with --bind_ip <address> to specify which IP
2020-01-05T10:24:03.731+0000 I CONTROL  [initandlisten] **          addresses it should serve responses from, or with --bind_ip_all to
2020-01-05T10:24:03.731+0000 I CONTROL  [initandlisten] **          bind to all interfaces. If this behavior is desired, start the
2020-01-05T10:24:03.732+0000 I CONTROL  [initandlisten] **          server with --bind_ip 127.0.0.1 to disable this warning.
2020-01-05T10:24:03.732+0000 I CONTROL  [initandlisten]
2020-01-05T10:24:03.733+0000 I CONTROL  [initandlisten]
2020-01-05T10:24:03.734+0000 I CONTROL  [initandlisten] ** WARNING: /sys/kernel/mm/transparent_hugepage/enabled is 'always'.
2020-01-05T10:24:03.734+0000 I CONTROL  [initandlisten] **        We suggest setting it to 'never'
2020-01-05T10:24:03.734+0000 I CONTROL  [initandlisten]
2020-01-05T10:24:03.738+0000 I STORAGE  [initandlisten] createCollection: admin.system.version with provided UUID: 2ecaac66-8c6f-403e-b789-2a69113c59fd
2020-01-05T10:24:03.802+0000 I COMMAND  [initandlisten] setting featureCompatibilityVersion to 4.0
2020-01-05T10:24:03.810+0000 I STORAGE  [initandlisten] createCollection: local.startup_log with generated UUID: 847e0215-cc4d-4f84-8bbe-0bccb2f9dfd3
2020-01-05T10:24:03.858+0000 I FTDC     [initandlisten] Initializing full-time diagnostic data capture with directory '/data/db/diagnostic.data'
2020-01-05T10:24:03.862+0000 I NETWORK  [initandlisten] waiting for connections on port 27017
2020-01-05T10:24:03.863+0000 I STORAGE  [LogicalSessionCacheRefresh] createCollection: config.system.sessions with generated UUID: 1e2b3be5-a92a-4eb8-b8a5-c6d10cfaadb7
2020-01-05T10:24:03.961+0000 I INDEX    [LogicalSessionCacheRefresh] build index on: config.system.sessions properties: { v: 2, key: { lastUse: 1 }, name: "lsidTTLIndex", ns: "config.system.sessions", expireAfterSeconds: 1800 }
2020-01-05T10:24:03.961+0000 I INDEX    [LogicalSessionCacheRefresh]     building index using bulk method; build may temporarily use up to 500 megabytes of RAM
2020-01-05T10:24:03.965+0000 I INDEX    [LogicalSessionCacheRefresh] build index done.  scanned 0 total records. 0 secs
2020-01-05T10:24:03.965+0000 I COMMAND  [LogicalSessionCacheRefresh] command config.$cmd command: createIndexes { createIndexes: "system.sessions", indexes: [ { key: { lastUse: 1 }, name: "lsidTTLIndex", expireAfterSeconds: 1800 } ], $db: "config" } numYields:0 reslen:114 locks:{ Global: { acquireCount: { r: 2, w: 2 } }, Database: { acquireCount: { w: 2, W: 1 } }, Collection: { acquireCount: { w: 2 } } } storage:{} protocol:op_msg 102ms

> OpenDataCam@2.1.0 start /opendatacam
> PORT=8080 NODE_ENV=production node server.js

Please specify the path to the raw detections file
-----------------------------------
-     Opendatacam initialized     -
- Config loaded:                  -
{
  "OPENDATACAM_VERSION": "2.1.0",
  "PATH_TO_YOLO_DARKNET": "/darknet",
  "VIDEO_INPUT": "usbcam",
  "NEURAL_NETWORK": "yolov3-tiny",
  "VIDEO_INPUTS_PARAMS": {
    "file": "opendatacam_videos/demo.mp4",
    "usbcam": "v4l2src device=/dev/video0 ! video/x-raw, framerate=30/1, width=640, height=360 ! videoconvert ! appsink",
    "usbcam_no_gstreamer": "-c 0",
    "experimental_raspberrycam_docker": "v4l2src device=/dev/video2 ! video/x-raw, framerate=30/1, width=640, height=360 ! videoconvert ! appsink",
    "raspberrycam_no_docker": "nvarguscamerasrc ! video/x-raw(memory:NVMM),width=1280, height=720, framerate=30/1, format=NV12 ! nvvidconv ! video/x-raw, format=BGRx, width=640, height=360 ! videoconvert ! video/x-raw, format=BGR ! appsink",
    "remote_cam": "YOUR IP CAM STREAM (can be .m3u8, MJPEG ...), anything supported by opencv"
  },
  "VALID_CLASSES": [
    "*"
  ],
  "DISPLAY_CLASSES": [
    {
      "class": "bicycle",
      "icon": "1F6B2.svg"
    },
    {
      "class": "person",
      "icon": "1F6B6.svg"
    },
    {
      "class": "truck",
      "icon": "1F69B.svg"
    },
    {
      "class": "motorbike",
      "icon": "1F6F5.svg"
    },
    {
      "class": "car",
      "icon": "1F697.svg"
    },
    {
      "class": "bus",
      "icon": "1F68C.svg"
    }
  ],
  "PATHFINDER_COLORS": [
    "#1f77b4",
    "#ff7f0e",
    "#2ca02c",
    "#d62728",
    "#9467bd",
    "#8c564b",
    "#e377c2",
    "#7f7f7f",
    "#bcbd22",
    "#17becf"
  ],
  "COUNTER_COLORS": {
    "yellow": "#FFE700",
    "turquoise": "#A3FFF4",
    "green": "#a0f17f",
    "purple": "#d070f0",
    "red": "#AB4435"
  },
  "NEURAL_NETWORK_PARAMS": {
    "yolov3": {
      "data": "cfg/coco.data",
      "cfg": "cfg/yolov3.cfg",
      "weights": "yolov3.weights"
    },
    "yolov3-tiny": {
      "data": "cfg/coco.data",
      "cfg": "cfg/yolov3-tiny.cfg",
      "weights": "yolov3-tiny.weights"
    },
    "yolov2-voc": {
      "data": "cfg/voc.data",
      "cfg": "cfg/yolo-voc.cfg",
      "weights": "yolo-voc.weights"
    }
  },
  "TRACKER_ACCURACY_DISPLAY": {
    "nbFrameBuffer": 300,
    "settings": {
      "radius": 3.1,
      "blur": 6.2,
      "step": 0.1,
      "gradient": {
        "1": "red",
        "0.4": "orange"
      },
      "canvasResolutionFactor": 0.1
    }
  },
  "MONGODB_URL": "mongodb://127.0.0.1:27017"
}
-----------------------------------
Process YOLO initialized
2020-01-05T10:24:09.844+0000 I NETWORK  [listener] connection accepted from 127.0.0.1:33770 #1 (1 connection now open)
> Ready on http://localhost:8080
> Ready on http://172.17.0.2:8080
2020-01-05T10:24:09.878+0000 I NETWORK  [conn1] received client metadata from 127.0.0.1:33770 conn1: { driver: { name: "nodejs", version: "3.2.5" }, os: { type: "Linux", name: "linux", architecture: "arm64", version: "4.9.140-tegra" }, platform: "Node.js v10.16.3, LE, mongodb-core: 3.2.5" }
2020-01-05T10:24:09.915+0000 I STORAGE  [conn1] createCollection: opendatacam.recordings with generated UUID: 0b545873-c40f-4232-8803-9c7c0cbd0ec4
2020-01-05T10:24:09.917+0000 I NETWORK  [listener] connection accepted from 127.0.0.1:33772 #2 (2 connections now open)
Success init db
2020-01-05T10:24:09.919+0000 I NETWORK  [conn2] received client metadata from 127.0.0.1:33772 conn2: { driver: { name: "nodejs", version: "3.2.5" }, os: { type: "Linux", name: "linux", architecture: "arm64", version: "4.9.140-tegra" }, platform: "Node.js v10.16.3, LE, mongodb-core: 3.2.5" }
2020-01-05T10:24:09.969+0000 I INDEX    [conn1] build index on: opendatacam.recordings properties: { v: 2, key: { dateEnd: -1 }, name: "dateEnd_-1", ns: "opendatacam.recordings" }
2020-01-05T10:24:09.969+0000 I INDEX    [conn1]          building index using bulk method; build may temporarily use up to 500 megabytes of RAM
2020-01-05T10:24:09.971+0000 I INDEX    [conn1] build index done.  scanned 0 total records. 0 secs
2020-01-05T10:24:09.971+0000 I STORAGE  [conn2] createCollection: opendatacam.tracker with generated UUID: 58e46bc1-6f22-4b3b-9e3f-42201351e5b4
2020-01-05T10:24:10.040+0000 I INDEX    [conn2] build index on: opendatacam.tracker properties: { v: 2, key: { recordingId: 1 }, name: "recordingId_1", ns: "opendatacam.tracker" }
2020-01-05T10:24:10.040+0000 I INDEX    [conn2]          building index using bulk method; build may temporarily use up to 500 megabytes of RAM
2020-01-05T10:24:10.042+0000 I INDEX    [conn2] build index done.  scanned 0 total records. 0 secs
2020-01-05T10:24:10.043+0000 I COMMAND  [conn2] command opendatacam.$cmd command: createIndexes { createIndexes: "tracker", indexes: [ { name: "recordingId_1", key: { recordingId: 1 } } ], lsid: { id: UUID("afed3446-90a2-4a09-b03b-ba2e9e3aa76f") }, $db: "opendatacam" } numYields:0 reslen:114 locks:{ Global: { acquireCount: { r: 2, w: 2 } }, Database: { acquireCount: { w: 2, W: 1 }, acquireWaitCount: { w: 1 }, timeAcquiringMicros: { w: 47016 } }, Collection: { acquireCount: { w: 2 } } } storage:{} protocol:op_msg 118ms
Process YOLO started
{ OPENDATACAM_VERSION: '2.1.0',
  PATH_TO_YOLO_DARKNET: '/darknet',
  VIDEO_INPUT: 'usbcam',
  NEURAL_NETWORK: 'yolov3-tiny',
  VIDEO_INPUTS_PARAMS:
   { file: 'opendatacam_videos/demo.mp4',
     usbcam:
      'v4l2src device=/dev/video0 ! video/x-raw, framerate=30/1, width=640, height=360 ! videoconvert ! appsink',
     usbcam_no_gstreamer: '-c 0',
     experimental_raspberrycam_docker:
      'v4l2src device=/dev/video2 ! video/x-raw, framerate=30/1, width=640, height=360 ! videoconvert ! appsink',
     raspberrycam_no_docker:
      'nvarguscamerasrc ! video/x-raw(memory:NVMM),width=1280, height=720, framerate=30/1, format=NV12 ! nvvidconv ! video/x-raw, format=BGRx, width=640, height=360 ! videoconvert ! video/x-raw, format=BGR ! appsink',
     remote_cam:
      'YOUR IP CAM STREAM (can be .m3u8, MJPEG ...), anything supported by opencv' },
  VALID_CLASSES: [ '*' ],
  DISPLAY_CLASSES:
   [ { class: 'bicycle', icon: '1F6B2.svg' },
     { class: 'person', icon: '1F6B6.svg' },
     { class: 'truck', icon: '1F69B.svg' },
     { class: 'motorbike', icon: '1F6F5.svg' },
     { class: 'car', icon: '1F697.svg' },
     { class: 'bus', icon: '1F68C.svg' } ],
  PATHFINDER_COLORS:
   [ '#1f77b4',
     '#ff7f0e',
     '#2ca02c',
     '#d62728',
     '#9467bd',
     '#8c564b',
     '#e377c2',
     '#7f7f7f',
     '#bcbd22',
     '#17becf' ],
  COUNTER_COLORS:
   { yellow: '#FFE700',
     turquoise: '#A3FFF4',
     green: '#a0f17f',
     purple: '#d070f0',
     red: '#AB4435' },
  NEURAL_NETWORK_PARAMS:
   { yolov3:
      { data: 'cfg/coco.data',
        cfg: 'cfg/yolov3.cfg',
        weights: 'yolov3.weights' },
     'yolov3-tiny':
      { data: 'cfg/coco.data',
        cfg: 'cfg/yolov3-tiny.cfg',
        weights: 'yolov3-tiny.weights' },
     'yolov2-voc':
      { data: 'cfg/voc.data',
        cfg: 'cfg/yolo-voc.cfg',
        weights: 'yolo-voc.weights' } },
  TRACKER_ACCURACY_DISPLAY:
   { nbFrameBuffer: 300,
     settings:
      { radius: 3.1,
        blur: 6.2,
        step: 0.1,
        gradient: [Object],
        canvasResolutionFactor: 0.1 } },
  MONGODB_URL: 'mongodb://127.0.0.1:27017' }
layer     filters    size              input                output
   0 (node:55) [DEP0001] DeprecationWarning: OutgoingMessage.flush is deprecated. Use flushHeaders instead.
conv     16  3 x 3 / 1   416 x 416 x   3   ->   416 x 416 x  16 0.150 BF
   1 max          2 x 2 / 2   416 x 416 x  16   ->   208 x 208 x  16 0.003 BF
   2 conv     32  3 x 3 / 1   208 x 208 x  16   ->   208 x 208 x  32 0.399 BF
   3 max          2 x 2 / 2   208 x 208 x  32   ->   104 x 104 x  32 0.001 BF
   4 conv     64  3 x 3 / 1   104 x 104 x  32   ->   104 x 104 x  64 0.399 BF
   5 max          2 x 2 / 2   104 x 104 x  64   ->    52 x  52 x  64 0.001 BF
   6 conv    128  3 x 3 / 1    52 x  52 x  64   ->    52 x  52 x 128 0.399 BF
   7 max          2 x 2 / 2    52 x  52 x 128   ->    26 x  26 x 128 0.000 BF
   8 conv    256  3 x 3 / 1    26 x  26 x 128   ->    26 x  26 x 256 0.399 BF
   9 max          2 x 2 / 2    26 x  26 x 256   ->    13 x  13 x 256 0.000 BF
  10 conv    512  3 x 3 / 1    13 x  13 x 256   ->    13 x  13 x 512 0.399 BF
  11 max          2 x 2 / 1    13 x  13 x 512   ->    13 x  13 x 512 0.000 BF
  12 conv   1024  3 x 3 / 1    13 x  13 x 512   ->    13 x  13 x1024 1.595 BF
  13 conv    256  1 x 1 / 1    13 x  13 x1024   ->    13 x  13 x 256 0.089 BF
  14 conv    512  3 x 3 / 1    13 x  13 x 256   ->    13 x  13 x 512 0.399 BF
  15 conv    255  1 x 1 / 1    13 x  13 x 512   ->    13 x  13 x 255 0.044 BF
  16 yolo
[yolo] params: iou loss: mse, iou_norm: 0.75, cls_norm: 1.00, scale_x_y: 1.00
  17 route  13
  18 conv    128  1 x 1 / 1    13 x  13 x 256   ->    13 x  13 x 128 0.011 BF
  19 upsample            2x    13 x  13 x 128   ->    26 x  26 x 128
  20 route  19 8
  21 conv    256  3 x 3 / 1    26 x  26 x 384   ->    26 x  26 x 256 1.196 BF
    "#2ca02c",
    "#d62728",
    "#9467bd",
    "#8c564b",
    "#e377c2",
    "#7f7f7f",
    "#bcbd22",
    "#17becf"
  ],
  "COUNTER_COLORS": {
    "yellow": "#FFE700",
    "turquoise": "#A3FFF4",
    "green": "#a0f17f",
    "purple": "#d070f0",
    "red": "#AB4435"
  },
  "NEURAL_NETWORK_PARAMS": {
    "yolov3": {
      "data": "cfg/coco.data",
      "cfg": "cfg/yolov3.cfg",
      "weights": "yolov3.weights"
    },
    "yolov3-tiny": {
      "data": "cfg/coco.data",
      "cfg": "cfg/yolov3-tiny.cfg",
      "weights": "yolov3-tiny.weights"
    },
    "yolov2-voc": {
      "data": "cfg/voc.data",
      "cfg": "cfg/yolo-voc.cfg",
      "weights": "yolo-voc.weights"
    }
  },
  "TRACKER_ACCURACY_DISPLAY": {
    "nbFrameBuffer": 300,
    "settings": {
      "radius": 3.1,
      "blur": 6.2,
      "step": 0.1,
      "gradient": {
        "1": "red",
        "0.4": "orange"
      },
      "canvasResolutionFactor": 0.1
    }
  },
  "MONGODB_URL": "mongodb://127.0.0.1:27017"
}
-----------------------------------
Process YOLO initialized
2020-01-05T10:34:44.353+0000 I NETWORK  [listener] connection accepted from 127.0.0.1:40190 #1 (1 connection now open)
> Ready on http://localhost:8080
> Ready on http://172.17.0.2:8080
2020-01-05T10:34:44.385+0000 I NETWORK  [conn1] received client metadata from 127.0.0.1:40190 conn1: { driver: { name: "nodejs", version: "3.2.5" }, os: { type: "Linux", name: "linux", architecture: "arm64", version: "4.9.140-tegra" }, platform: "Node.js v10.16.3, LE, mongodb-core: 3.2.5" }
2020-01-05T10:34:44.424+0000 I NETWORK  [listener] connection accepted from 127.0.0.1:40192 #2 (2 connections now open)
Success init db
2020-01-05T10:34:44.430+0000 I NETWORK  [conn2] received client metadata from 127.0.0.1:40192 conn2: { driver: { name: "nodejs", version: "3.2.5" }, os: { type: "Linux", name: "linux", architecture: "arm64", version: "4.9.140-tegra" }, platform: "Node.js v10.16.3, LE, mongodb-core: 3.2.5" }
(node:52) [DEP0001] DeprecationWarning: OutgoingMessage.flush is deprecated. Use flushHeaders instead.
```

