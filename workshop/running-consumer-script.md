# Running Consumer Script for Pico

## Run the below Docker container for preparing environment for Consumer scripts

```
docker run -d -p 5000:5000 ajeetraina/opencv4-python3 bash
```

## Open up bash shell inside Docker Container

```
docker exec -it <container-id> bash
```

## Remove the existing Pico directory

```
rm -fr pico
```

## Cloning the fresh Repository

```
#git clone https://github.com/collabnix/pico
```

## Locating the right consumer scripts

You will need 2 scripts - Image Processor and Consumer

```
cd pico/deployment/objects/
```

## Execute Image processor Script placed under https://github.com/collabnix/pico/blob/master/deployment/objects/image_processor.py

```
python3 image_processor.py
```

## Open up new bash again

```
docker exec -it <container-id> bash
```

## Exexute Consumer Script

```
python3 consumer.py
```
