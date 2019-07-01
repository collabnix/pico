
# Preparing AWS Lambda Deployment Package in Python for Apache Kafka & Testing it on AWS Lambda 

A deployment package is a ZIP archive that contains your function code and dependencies. You need to create a deployment package if you use the Lambda API to manage functions, or if you need to include libraries and dependencies other than the AWS SDK. You can upload the package directly to Lambda, or you can use an Amazon S3 bucket, and then upload it to Lambda. If the deployment package is larger than 50 MB, you must use Amazon S3.

Let us try out a simple example to build Kafka Module and package it in the form of zip which can be loaded onto AWS Lambda.


## Pre-requisite:

- Docker Desktop for Mac
- Python 3.6 

## Using a Virtual Environment

You may need to use a virtual environment to install dependencies for your function. This can occur if your function or its dependencies have dependencies on native libraries, or if you used Homebrew to install Python.

To update a Python function with a virtual environment

1. Create a virtual environment.

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

For Python 3.3 and newer, you need to use the built-in venv module to create a virtual environment, instead of installing virtualenv.

```
[Captains-Bay]🚩 >  python3 -m venv v-env
[Captains-Bay]🚩 > 
```

## Activate the environment

```
source v-env/bin/activate
```

## Install libraries with pip

```
(v-env) [Captains-Bay]🚩 >  pip install kafka
Collecting kafka
  Downloading https://files.pythonhosted.org/packages/21/71/73286e748ac5045b6a669c2fe44b03ac4c5d3d2af9291c4c6fc76438a9a9/kafka-1.3.5-py2.py3-none-any.whl (207kB)
     |████████████████████████████████| 215kB 428kB/s 
Installing collected packages: kafka
Successfully installed kafka-1.3.5
(v-env) [Captains-Bay]🚩 > 
```

## Deactivate the virtual environment

```
deactivate
```

## Create a ZIP archive with the contents of the library

```
cd v-env/lib/python3.7/site-packages
```



```
zip -r9 ${OLDPWD}/function.zip .
```

```
cd $OLDPWD
```

## Add your function code to the archive

Add [function.py](https://github.com/collabnix/pico/blob/master/lambda/function.py) here under the same directory

```
zip -g function.zip function.py
```


