# Running Apache Kafka on 2-Node Docker Swarm Cluster

Apache Kafka is an open-source stream-processing software platform developed by LinkedIn and donated to the Apache Software Foundation. It is written in Scala and Java. The project aims to provide a unified, high-throughput, low-latency platform for handling real-time data feeds.

Apache Kafka is a distributed, partitioned, and replicated publish-subscribe messaging system that is used to send high volumes of data, in the form of messages, from one point to another. It replicates these messages across a cluster of servers in order to prevent data loss and allows both online and offline message consumption. This in turn shows the fault-tolerant behaviour of Kafka in the presence of machine failures that also supports low latency message delivery. In a broader sense, Kafka is considered as a unified platform which guarantees zero data loss and handles real-time data feeds.

## Cloning the Repository

```
git clone https://github.com/collabnix/pico
cd pico/kafka/
```

## Building up Kafka Application

```
git clone https://github.com/collabnix/pico
cd pico/kafka
```

```
docker stack deploy -c docker-compose.yml mykafka
```

By now, you should be able to access kafka manager at https://<IP>:9000
    
## Adding a cluster 

- Cluster Name = pico (or whatever you want)
- Cluster Zookeeper Hosts = zk-1:2181,zk-2:2181,zk-3:2181
- Kafka Version = leave it at 0.9.01 even though we're running 1.0.0
- Enable JMX Polling = enabled

## Adding a Topic

Click on Topic on the top center of the Kafka Manager to create a new topic with the below details -

- Topic = testpico
- Partitions = 6
- Replication factor = 2

which gives an even spread of the topic across the three kafka nodes.

While saving the settings, it might ask to set minimal parameter required. Feel free to follow the instruction provided.
    
[Next >> Pushing a Sample Video to Kafka Cluster using python script](https://github.com/collabnix/pico/blob/master/kafka/producer-consumer.md)
