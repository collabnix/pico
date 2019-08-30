FROM ajeetraina/opencv4-python3
RUN apt update
RUN mkdir -p /project/
WORKDIR /project/
RUN git clone https://github.com/collabnix/pico
WORKDIR /project/pico/deployment/objects/
RUN pip3 install pytz boto3 pillow
CMD ["pico-consumer.py"]
ENTRYPOINT ["python3"]
