FROM ajeetraina/rpi-raspbian-opencv
MAINTAINER Ajeet S Raina "ajeetraina@gmail.c
RUN apt update -y
RUN pip3 install pytz
RUN pip3 install kafka-python
RUN apt install -y git
ADD . /pico/
WORKDIR /pico/
ENTRYPOINT ["python3", "/pico/plates-producer.py" ]
