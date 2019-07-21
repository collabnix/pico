FROM ajeetraina/opencv-python3-armv7
MAINTAINER Ajeet S Raina "ajeetraina@gmail.com"

RUN pip3 install opencv2-python
RUN git clone https://github.com/collabnix/pico && \
     cd pico/deployment/raspbi
WORKDIR /pico/deployment/raspbi
ENTRYPOINT [ "python3", "producer.py" ]
