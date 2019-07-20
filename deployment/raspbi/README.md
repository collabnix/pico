# Getting Raspberry Pi Ready & Tagged with Camera IDs

## Pre-requisite

- Enable Raspberry Pi Camera Interface using raspbi-config utility
- Enable the BCM module:

```
sudo modprobe bcm2835-v4l2
```

## Method #1 - The "Undocker Way"

Install these below packages manually


```
pip3 install opencv-python
sudo apt-get install libatlas-base-dev
sudo apt-get install libjasper-dev
sudo apt-get install libqtgui4
sudo apt-get install python3-pyqt5
apt-get install libqt4-test
pip3 install kafka-python
pip3 install pytz
```

## Initiate the producer script

Before you initiate, modify the camera ID on each of these camera modules, tagged with IP.
Say, your node IP is 192.168.1.6, mark the camera ID as "6"

```
python3 producer.py
```
