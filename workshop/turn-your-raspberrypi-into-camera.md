
# Turn Your Raspberry Pi into CCTV Camera


## Cloning the Repository:


```
$ git clone https://github.com/collabnix/docker-cctv-raspbian
```

## Building Docker Image

```
$ cd docker-cctv-raspbian
$ docker build -t collabnix/docker-cctv-raspbian .
```

## Configuring Camera Interface

Before you execute run.sh, you need to configure Camera Interface by running the below command:

```
$sudo raspi-config
```

It will open up command-line UI window, choose Interfacing , select Camera and enable it. Save and exit the CLI window.

## Running the Docker container

Before you execute run.sh, you will need to load the required driver “bcm2835-v412” to make your camera module work. If you miss this step, you will end up seeing a blank screen even though the application comes up without any issue.

```
$ sudo modprobe bcm2835-v4l2
```

```
$sudo sh run.sh
```

That’s it. Browse over to http://<IP>:8082(either using Win Laptop or macbook) to open up CCTV cam which initiates the video streaming instantly. Cool, isn’t it?


