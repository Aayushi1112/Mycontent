#!/bin/bash
crond
env
export ECS_INSTANCE_IP_ADDRESS=$(curl -s 169.254.170.2/v2/metadata | jq -r ".Containers[0].Networks[0].IPv4Addresses[0]")
# The environment variables are already set up by the Dockerfile
java $JAVA_OPTS -Djava.rmi.server.hostname=${ECS_INSTANCE_IP_ADDRESS} $APM -Djava.security.egd=file:/dev/urandom -Dspring.profiles.active=docker -Dlogging.file.path=/var/log -jar ${APP_JAR_NAME}-${APP_JAR_VERSION}.jar
#Updated
