# Building Docker Image for Number Plates Detection Service

```
docker build -t ajeetraina/pico-raspbi-producer .
```

## Running the Container

```
docker run -dit --privileged -v --device=/dev/vcsm --device=/dev/vchiq  -v /dev/video0:/dev/video0  ajeetraina/pico-raspbi-producer 
```


