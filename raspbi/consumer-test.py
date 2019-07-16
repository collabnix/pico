
# coding: utf-8

# In[3]:


import datetime
from kafka import KafkaConsumer


topic = "testpico"
brokers = ["35.189.130.4:9092"]

consumer = KafkaConsumer(
    topic, 
    bootstrap_servers=brokers,
    value_deserializer=lambda m: json.loads(m.decode('utf-8')))


# In[4]:


for msg in consumer:
    print(msg.value['frame'])


# In[ ]:



