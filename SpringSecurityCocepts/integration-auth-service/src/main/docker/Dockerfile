#-----------------------------------------------------------------------------------------------------
# Integration Api Gateway
#-----------------------------------------------------------------------------------------------------
FROM 155086999298.dkr.ecr.us-east-1.amazonaws.com/cicd/alpine3.17-openjdk17:latest

MAINTAINER FranConnect <support@franconnect.com>

# Keep consistent with build.gradle 

ENV APP_JAR_NAME integration-auth-service
ENV APP_JAR_VERSION 0.0.1

ADD ${APP_JAR_NAME}-${APP_JAR_VERSION}.jar /app/
ADD run.sh /app/

RUN chmod +x /app/* 

WORKDIR /app

ENTRYPOINT ["/bin/bash","-c"]
CMD ["/app/run.sh"]


