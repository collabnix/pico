# Running Apache Kafka on 2-Node Docker Swarm Cluster

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
