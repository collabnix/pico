# Getting Raspberry Pi Ready & Tagged with Camera IDs

## Pre-requisite

- Enable Raspberry Pi Camera Interface using raspbi-config utility
- Enable the BCM module:

```
sudo modprobe bcm2835-v4l2
```

## Cloning the Repository

```
git clone https://github.com/collabnix/pico/
cd pico/deployment/raspbi
```

## Modifying the Camera ID:1,2,3 respectively

## Building Docker Image

```
docker build -t ajeetraina/pico-armv71
```

## Running the Container

```
docker run -d --privileged -v /dev/video0:/dev/video0 ajeetraina/pico-armv71
```
