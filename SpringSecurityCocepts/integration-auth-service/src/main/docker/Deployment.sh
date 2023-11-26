#!/bin/bash

aws --version
echo "APP_NAME                ENV_NAME     BRANCH_NAME COMMIT_ID BUILD_NUMBER CLUSTER_NAME ECR_REPO_URL"
echo "$1       $2       $3          $4        $5           $6                  $7"
APP_NAME="${1}"
ENV_NAME="${2}"
BRANCH_NAME="${3}"
COMMIT_ID="${4}"
BUILD_NUMBER="${5}"
CLUSTER_NAME="${6}"
ECR_REPO_URL="${7}"
ACCOUNT_ID=$(aws sts get-caller-identity --query 'Account' --output text)
REGION_AWS=$(aws configure list | grep region | awk '{print $2}')
NEW_DOCKER_IMAGE_LOG_CONTAINER="${ACCOUNT_ID}.dkr.ecr.${REGION_AWS}.amazonaws.com/cicd/aws-for-fluent-bit:latest"
TASK_FAMILY=$(aws ecs list-services --cluster $CLUSTER_NAME | grep -w `echo $APP_NAME | tr "-" " " | sed -e "s/\b\(.\)/\u\1/g" | sed 's/ //g'` | cut -d '/' -f3 | cut -d '"' -f1)
LOG_CONTAINER_NAME="fluentbit"
if [ -z $TASK_FAMILY ]; then
 TASK_FAMILY="${APP_NAME}"
fi
 
CAPS_APP_NAME="$(echo $APP_NAME | tr 'a-z' 'A-Z')"
NEW_DOCKER_IMAGE="${ECR_REPO_URL}/${1}:${3}_${4}_${5}"


#UPDATION TASK DEF
TASK_DEFINITION_NAME=$(aws ecs describe-services --services $TASK_FAMILY --cluster $CLUSTER_NAME | jq .'services[].taskDefinition' | cut -d '"' -f2 | cut -d '/' -f2 | cut -d ':' -f1 )
OLD_TASK_DEF=$(aws ecs describe-task-definition --task-definition $TASK_DEFINITION_NAME --output json)
#N#NEW_TASK_DEF=$(echo $OLD_TASK_DEF | jq --arg NDI $NEW_DOCKER_IMAGE '.taskDefinition.containerDefinitions[0].image=$NDI')

NO_OF_CONTAINERS=$(echo  $OLD_TASK_DEF | jq '.taskDefinition.containerDefinitions | length')
NO_OF_CONTAINERS=$(expr $NO_OF_CONTAINERS - 1)
for i in $(seq 0 $NO_OF_CONTAINERS)
do
PLACE_CONTINER_NAME=$(echo $OLD_TASK_DEF | jq ".taskDefinition.containerDefinitions[$i].name" --raw-output)
if [ "$PLACE_CONTINER_NAME" == "$APP_NAME" ]; then
NEW_TASK_DEF=$(echo $OLD_TASK_DEF | jq --arg NDI $NEW_DOCKER_IMAGE --argjson i $i '.taskDefinition.containerDefinitions[$i].image=$NDI')
fi
done

FINAL_TASK=$(echo $NEW_TASK_DEF | jq '.taskDefinition|{requiresCompatibilities: .requiresCompatibilities, memory: .memory, networkMode: .networkMode, cpu: .cpu, family: .family, volumes: .volumes, containerDefinitions: .containerDefinitions, executionRoleArn: .executionRoleArn, taskRoleArn: .taskRoleArn}')
echo "*****************************Final TASK"
echo $FINAL_TASK
aws ecs register-task-definition --family $TASK_DEFINITION_NAME --cli-input-json "$(echo $FINAL_TASK)"
sleep 5
aws ecs update-service --service $TASK_FAMILY --task-definition $TASK_DEFINITION_NAME --cluster $CLUSTER_NAME --force-new-deployment
echo "*****************************NEW TASK UPDATEED"
LATEST_TASK_DEF=$(aws ecs describe-services --services $TASK_FAMILY --cluster $CLUSTER_NAME | jq .'services[].taskDefinition' | cut -d '"' -f2)
aws ecs describe-task-definition --task-definition $LATEST_TASK_DEF | grep -i $NEW_DOCKER_IMAGE
if [ $? -eq 0 ]; then
    echo "*****************************Service Deployement has Completed"
    echo -e '{"text":"*'$CAPS_APP_NAME'* Service has updated with docker image tag: *'$3_$4_$5'* on MSA *'$CLUSTER_NAME'* cluster at '`date +%d-%B-%Y_%H.%M_%Z`.'"}' | curl -X POST -H 'Content-type: application/json' --data-binary @- https://franconnect.ryver.com/application/webhook/DKMc5TUn3ACLpL7
    echo -e '{"text":"*'$CAPS_APP_NAME'* Service has updated with docker image tag: *'$3_$4_$5'* on MSA *'$CLUSTER_NAME'* cluster at '`date +%d-%B-%Y_%H.%M_%Z`.'"}' | curl -X POST -H 'Content-type: application/json' --data-binary @- https://outlook.office.com/webhook/81ae8c9e-117c-4623-81c5-8d6c65fe548b@864c302d-1eff-4f58-8e04-318cf78a18ac/IncomingWebhook/520e716a2cab45be9fc10201c40bb550/aefc0d3f-cbaa-41d2-86a0-1fa2a14227f4
 else
    echo "FAIL************************************************************************"
    exit 5555
fi


