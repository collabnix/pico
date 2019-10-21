# How to setup Docker Swarm on AWS Cloud

## Pre-requisites:

- Docker Desktop for Mac or Windows
- AWS Account ( You will require t2.medium instances for this)
- AWS CLI installed

## Adding Your Credentials:

```
[Captains-Bay]🚩 >  cat ~/.aws/credentials
[default]
aws_access_key_id = XXXA 
aws_secret_access_key = XX
```

## Verifying AWS Version


```
[Captains-Bay]🚩 >  aws --version
aws-cli/1.11.107 Python/2.7.10 Darwin/17.7.0 botocore/1.5.70
Setting up Environmental Variable
```

```
[Captains-Bay]🚩 >  export VPC=vpc-ae59f0d6
[Captains-Bay]🚩 >  export REGION=us-west-2a
[Captains-Bay]🚩 >  export SUBNET=subnet-827651c9
[Captains-Bay]🚩 >  export ZONE=a
[Captains-Bay]🚩 >  export REGION=us-west-2
```

## Building up First Node using Docker Machine

```
[Captains-Bay]🚩 >  docker-machine create  --driver amazonec2  --amazonec2-access-key=${ACCESS_KEY_ID}  --amazonec2-secret-key=${SECRET_ACCESS_KEY} --amazonec2-region=us-west-2 --amazonec2-vpc-id=vpc-ae59f0d6 --amazonec2-ami=ami-78a22900 --amazonec2-open-port 2377 --amazonec2-open-port 7946 --amazonec2-open-port 4789 --amazonec2-open-port 7946/udp --amazonec2-open-port 4789/udp --amazonec2-open-port 8080 --amazonec2-open-port 443 --amazonec2-open-port 80 --amazonec2-subnet-id=subnet-72dbdb1a --amazonec2-instance-type=t2.micro kafka-swarm-node1
```

## Listing out the Nodes

```
[Captains-Bay]🚩 >  docker-machine ls
NAME                ACTIVE   DRIVER      STATE     URL                         SWARM   DOCKER     ERRORS
kafka-swarm-node1   -        amazonec2   Running   tcp://35.161.106.158:2376           v18.09.6   
kafka-swarm-node2   -        amazonec2   Running   tcp://54.201.99.75:2376             v18.09.6 
```

## Initialiating Docker Swarm Manager Node

```
ubuntu@kafka-swarm-node1:~$ sudo docker swarm init --advertise-addr 172.31.53.71 --listen-addr 172.31.53.71:2377
Swarm initialized: current node (yui9wqfu7b12hwt4ig4ribpyq) is now a manager.

To add a worker to this swarm, run the following command:

    docker swarm join --token SWMTKN-1-xxxxxmr075to2v3k-decb975h5g5da7xxxx 172.31.53.71:2377

To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.
```

## Adding Worker Node


```
ubuntu@kafka-swarm-node2:~$ sudo docker swarm join --token SWMTKN-1-2xjkynhin0n2zl7xxxk-decb975h5g5daxxxxxxxxn 172.31.53.71:2377
This node joined a swarm as a worker.
```

## Verifying 2-Node Docker Swarm Mode Cluster

```
ubuntu@kafka-swarm-node1:~$ sudo docker node ls
ID                            HOSTNAME            STATUS              AVAILABILITY        MANAGER STATUS      ENGINE VERSION
yui9wqfu7b12hwt4ig4ribpyq *   kafka-swarm-node1   Ready               Active              Leader              18.09.6
vb235xtkejim1hjdnji5luuxh     kafka-swarm-node2   Ready               Active                                  18.09.6
```

## Installing Docker Compose

```
curl -L https://github.com/docker/compose/releases/download/1.25.0-rc1/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   617    0   617    0     0   2212      0 --:--:-- --:--:-- --:--:--  2211
100 15.5M  100 15.5M    0     0  8693k      0  0:00:01  0:00:01 --:--:-- 20.1M
```

```
root@kafka-swarm-node1:/home/ubuntu/dockerlabs/solution/kafka-swarm# chmod +x /usr/local/bin/docker-compose
```

```
ubuntu@kafka-swarm-node1:~/dockerlabs/solution/kafka-swarm$ sudo docker-compose version
docker-compose version 1.25.0-rc1, build 8552e8e2
docker-py version: 4.0.1
CPython version: 3.7.3
OpenSSL version: OpenSSL 1.1.0j  20 Nov 2018
```



