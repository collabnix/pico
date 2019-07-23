FROM collabnix/rpi-raspbian-opencv
MAINTAINER Ajeet S Raina "ajeetraina@gmail.c

RUN apt update -y
RUN pip3 install pytz
RUN pip3 install kafka-python
RUN apt install -y git
ADD . /pico/
WORKDIR /pico/raspbi/
RUN sudo modprobe bcm2835-v4l2
ENTRYPOINT ["python3", "/pico/producer.py" ]
