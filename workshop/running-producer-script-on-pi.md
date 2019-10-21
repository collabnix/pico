# Running Producer Script on Pi

## Clone the Repository

```
git clone https://github.com/collabnix/pico
```

## Locating Producer Script

```
cd pico/deployment/objects/
```

## Edit producer_camera.py script and add the proper IP address for the kafka broker:

```
brokers = ["35.221.213.182:9092"]
```

## Installing Dependencies

```
apt install -y python-pip libatlas-base-dev libjasper-dev libqtgui4 python3-pyqt5 python3-pyqt5 libqt4-test
pip3 install kafka-python opencv-python pytz
pip install virtualenv virtualenvwrapper numpy
```

## Execute the script

```
python3 producer_camera.py
```

Please Note: This script should be run post the consumer scripts (Image_Processor & Consumer.py) is executed
