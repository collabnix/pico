FROM fradelg/rpi-opencv

MAINTAINER ajeetraina@gmail.com

RUN apt-get update
RUN apt install python-pip python3-pip
RUN pip3 install pytz kafka-python
RUN pip install virtualenv virtualenvwrapper
RUN apt-get install -y git
RUN git clone https://github.com/collabnix/pico
WORKDIR pico/deployment/objects
