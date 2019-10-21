

# Installing Docker 18.09 on Raspberry Pi

Its a single liner command. -L means location, -s means silent and -S means show error.

```
root@raspberrypi:~# curl -sSL https://get.docker.com/ | sh
# Executing docker install script, commit: 40b1b76
+ sh -c apt-get update -qq >/dev/null
+ sh -c apt-get install -y -qq apt-transport-https ca-certificates curl >/dev/null
+ sh -c curl -fsSL "https://download.docker.com/linux/raspbian/gpg" | apt-key add -qq - >/dev/null
Warning: apt-key output should not be parsed (stdout is not a terminal)
+ sh -c echo "deb [arch=armhf] https://download.docker.com/linux/raspbian stretch edge" > /etc/apt/sources.list.d/docker.list
+ sh -c apt-get update -qq >/dev/null
+ sh -c apt-get install -y -qq --no-install-recommends docker-ce >/dev/null
+ sh -c docker version
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
If you would like to use Docker as a non-root user, you should now consider
adding your user to the "docker" group with something like:

  sudo usermod -aG docker your-user

Remember that you will have to log out and back in for this to take effect!

WARNING: Adding a user to the "docker" group will grant the ability to run
         containers which can be used to obtain root privileges on the
         docker host.
         Refer to https://docs.docker.com/engine/security/security/#docker-daemon-attack-surface
         for more information.

** DOCKER ENGINE - ENTERPRISE **

If you’re ready for production workloads, Docker Engine - Enterprise also includes:

  * SLA-backed technical support
  * Extended lifecycle maintenance policy for patches and hotfixes
  * Access to certified ecosystem content

** Learn more at https://dockr.ly/engine2 **

ACTIVATE your own engine to Docker Engine - Enterprise using:

  sudo docker engine activate

```

## Verifying Docker Version

```
root@raspberrypi:~# docker version
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
root@raspberrypi:~#
```


## Deploying Nginx App

```
root@raspberrypi:~# docker run -d -p 80:80 nginx
Unable to find image 'nginx:latest' locally
latest: Pulling from library/nginx
9c38b5a8a4d5: Pull complete
1c9b1b3e1e0d: Pull complete
258951b5612f: Pull complete
Digest: sha256:dd2d0ac3fff2f007d99e033b64854be0941e19a2ad51f174d9240dda20d9f534
Status: Downloaded newer image for nginx:latest
d812bf50d136b0f78353f0a0c763b6b08ecc5e7ce706bac8bd660cdd723e0fcd
root@raspberrypi:~#
```

## 

```
root@raspberrypi:~# curl localhost:80
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
<style>
    body {
        width: 35em;
        margin: 0 auto;
        font-family: Tahoma, Verdana, Arial, sans-serif;
    }
</style>
</head>
<body>
<h1>Welcome to nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx.</em></p>
</body>
</html>
root@raspberrypi:~#
```

## 

```
root@raspberrypi:~# docker info
Containers: 1
 Running: 1
 Paused: 0
 Stopped: 0
Images: 1
Server Version: 18.09.0
Storage Driver: overlay2
 Backing Filesystem: extfs
 Supports d_type: true
 Native Overlay Diff: true
Logging Driver: json-file
Cgroup Driver: cgroupfs
Plugins:
 Volume: local
 Network: bridge host macvlan null overlay
 Log: awslogs fluentd gcplogs gelf journald json-file local logentries splunk syslog
Swarm: inactive
Runtimes: runc
Default Runtime: runc
Init Binary: docker-init
containerd version: 9754871865f7fe2f4e74d43e2fc7ccd237edcbce
runc version: 09c8266bf2fcf9519a651b04ae54c967b9ab86ec
init version: fec3683
Security Options:
 seccomp
  Profile: default
Kernel Version: 4.14.98-v7+
Operating System: Raspbian GNU/Linux 9 (stretch)
OSType: linux
Architecture: armv7l
CPUs: 4
Total Memory: 927.2MiB
Name: raspberrypi
ID: FEUI:RVU6:AWPZ:6P22:TSLT:FDJC:CBIB:D2NU:AQEQ:IHVH:HFRY:HYWF
Docker Root Dir: /var/lib/docker
Debug Mode (client): false
Debug Mode (server): false
Registry: https://index.docker.io/v1/
Labels:
Experimental: false
Insecure Registries:
 127.0.0.0/8
Live Restore Enabled: false
Product License: Community Engine

WARNING: No memory limit support
WARNING: No swap limit support
WARNING: No kernel memory limit support
WARNING: No oom kill disable support
WARNING: No cpu cfs quota support
WARNING: No cpu cfs period support
```



## Verifying Dockerd


```
root@raspberrypi:~/hellowhale# systemctl status docker
● docker.service - Docker Application Container Engine
   Loaded: loaded (/lib/systemd/system/docker.service; enabled; vendor preset: e
   Active: active (running) since Tue 2019-02-26 13:01:04 IST; 38min ago
     Docs: https://docs.docker.com
 Main PID: 2437 (dockerd)
      CPU: 1min 46.174s
   CGroup: /system.slice/docker.service
           ├─2437 /usr/bin/dockerd -H unix://
           ├─2705 /usr/bin/docker-proxy -proto tcp -host-ip 0.0.0.0 -host-port 8
           └─4186 /usr/bin/docker-proxy -proto tcp -host-ip 0.0.0.0 -host-port 8

Feb 26 13:37:06 raspberrypi dockerd[2437]: time="2019-02-26T13:37:06.400368104+0
Feb 26 13:37:06 raspberrypi dockerd[2437]: time="2019-02-26T13:37:06.402012958+0
Feb 26 13:37:06 raspberrypi dockerd[2437]: time="2019-02-26T13:37:06.402634316+0
Feb 26 13:37:06 raspberrypi dockerd[2437]: time="2019-02-26T13:37:06.403005881+0
Feb 26 13:37:06 raspberrypi dockerd[2437]: time="2019-02-26T13:37:06.408358205+0
Feb 26 13:37:06 raspberrypi dockerd[2437]: time="2019-02-26T13:37:06.810154786+0
Feb 26 13:37:06 raspberrypi dockerd[2437]: time="2019-02-26T13:37:06.810334839+0
Feb 26 13:37:06 raspberrypi dockerd[2437]: time="2019-02-26T13:37:06.811462659+0
Feb 26 13:37:06 raspberrypi dockerd[2437]: time="2019-02-26T13:37:06.811768546+0
Feb 26 13:37:07 raspberrypi dockerd[2437]: time="2019-02-26T13:37:07.402282796+0
```


## Verifying if armv7 hello-world image is available or not

```
docker run --rm mplatform/mquery hello-world
Unable to find image 'mplatform/mquery:latest' locally
latest: Pulling from mplatform/mquery
db6020507de3: Pull complete
5107afd39b7f: Pull complete
Digest: sha256:e15189e3d6fbcee8a6ad2ef04c1ec80420ab0fdcf0d70408c0e914af80dfb107
Status: Downloaded newer image for mplatform/mquery:latest
Image: hello-world
 * Manifest List: Yes
 * Supported platforms:
   - linux/amd64
   - linux/arm/v5
   - linux/arm/v7
   - linux/arm64
   - linux/386
   - linux/ppc64le
   - linux/s390x
   - windows/amd64:10.0.14393.2551
   - windows/amd64:10.0.16299.846
   - windows/amd64:10.0.17134.469
   - windows/amd64:10.0.17763.194
```


## Verifying hellowhale Image 

```
root@raspberrypi:~# docker run --rm mplatform/mquery ajeetraina/hellowhale
Image: ajeetraina/hellowhale
 * Manifest List: No
 * Supports: amd64/linux
````

## Verifying Random Images

```
root@raspberrypi:~# docker run --rm mplatform/mquery rycus86/prometheus
Image: rycus86/prometheus
 * Manifest List: Yes
 * Supported platforms:
   - linux/amd64
   - linux/arm/v7
   - linux/arm64
```

[Next >> Setting up Apache Kafka on Cloud Platform](https://github.com/collabnix/pico)
