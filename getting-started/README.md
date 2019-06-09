# Installing Raspbian OS on Raspberry Pi

Setup a Raspberry Pi with Raspbian Jessie <br />
https://www.raspberrypi.org/downloads/raspbian/ <br />


### Enable SSH 

```
service ssh start
```

### Enable SSH on boot-time

```
echo "service ssh start" >> /etc/rc.local
```

### Installing Docker

```
curl -sSL https://get.docker.com/ | sh
```

### Installing Docker Compose

```
apt install python-pip
pip install docker-compose
```

### Start Docker

```
root@node2:~# systemctl start docker
root@node2:~# docker version
Client:
 Version:           18.09.0
 API version:       1.39
 Go version:        go1.10.4
 Git commit:        4d60db4
 Built:             Wed Nov  7 00:57:21 2018
 OS/Arch:           linux/arm
 Experimental:      false

Server: Docker Engine - Community
 Engine:
  Version:          18.09.0
  API version:      1.39 (minimum version 1.12)
  Go version:       go1.10.4
  Git commit:       4d60db4
  Built:            Wed Nov  7 00:17:57 2018
  OS/Arch:          linux/arm
  Experimental:     false
root@node2:~# clear
root@node2:~# docker version
Client:
 Version:           18.09.0
 API version:       1.39
 Go version:        go1.10.4
 Git commit:        4d60db4
 Built:             Wed Nov  7 00:57:21 2018
 OS/Arch:           linux/arm
 Experimental:      false

Server: Docker Engine - Community
 Engine:
  Version:          18.09.0
  API version:      1.39 (minimum version 1.12)
  Go version:       go1.10.4
  Git commit:       4d60db4
  Built:            Wed Nov  7 00:17:57 2018
  OS/Arch:          linux/arm
  Experimental:     false
root@node2:~# ls
docker-cctv-raspbian              portainer-agent-stack.yml      tiny-cloud
dockerlabs                        real_time_object_detection.py  v19.03.0-beta1.zip
MobileNetSSD_deploy.prototxt.txt  rpi-motion-mmal
pi_object_detection.py            tf-opencv
```

[Next >> Setting up Apache Kafka on Cloud Platform](https://github.com/collabnix/pico)
