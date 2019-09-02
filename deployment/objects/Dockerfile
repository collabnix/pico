FROM ajeetraina/opencv4-python3
RUN apt update
RUN mkdir -p /project/
COPY image_processor.py /project/
COPY image-processor.json /project/
COPY consumer.py /project/
COPY templates /project/
COPY templates/index.html /project/templates/index.html
COPY pico-consumer.sh /project/
WORKDIR /project/
RUN pip3 install pytz boto3 pillow
CMD ["pico-consumer.sh"]
ENTRYPOINT ["/bin/sh"]
