
#!/usr/bin/env bash

: ${SUSPEND:='n'}

set -e

export KAFKA_JMX_OPTS="-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=${SUSPEND},address=5005"
export CLASSPATH="$(find target/kafka-connect-aws-lambda-1.0-package/share/java -type f -name '*.jar' | tr '\n' ':')"

# connect-standalone config/connect-json-docker.properties config/AwsLambdaSinkConnector.properties

connect-standalone $1 $2
