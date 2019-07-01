
# Preparing AWS Lambda Deployment Package in Python for Apache Kafka


```
[Captains-Bay]🚩 >  pwd
/Users/ajeetraina/pico
[Captains-Bay]🚩 >  virtualenv v-env
Using base prefix '/Library/Frameworks/Python.framework/Versions/3.6'
New python executable in /Users/ajeetraina/pico/v-env/bin/python3.6
Also creating executable in /Users/ajeetraina/pico/v-env/bin/python
Installing setuptools, pip, wheel...
done.
[Captains-Bay]🚩 
```

```
[Captains-Bay]🚩 >  python3 -m venv v-env
[Captains-Bay]🚩 > 
```

```
source v-env/bin/activate
```

```
(v-env) [Captains-Bay]🚩 >  pip install kafka
Collecting kafka
  Downloading https://files.pythonhosted.org/packages/21/71/73286e748ac5045b6a669c2fe44b03ac4c5d3d2af9291c4c6fc76438a9a9/kafka-1.3.5-py2.py3-none-any.whl (207kB)
     |████████████████████████████████| 215kB 428kB/s 
Installing collected packages: kafka
Successfully installed kafka-1.3.5
(v-env) [Captains-Bay]🚩 > 
```

```
deactivate
```

```
cd v-env/lib/python3.7/site-packages
```

```
zip -r9 ${OLDPWD}/function.zip .
```

```
cd $OLDPWD
```

```
zip -g function.zip function.py
```

