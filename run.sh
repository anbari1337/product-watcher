#!/bin/bash

DOCKER_IMAGE_NAME="product-watcher"
DOCKER_IMAGE_TAG="1.0"

PRODUCT_WATCHER_CONTAINER="product-watcher-service"
POSTGRES_CONTAINER="postgres"

docker-compose down

docker rm -f $PRODUCT_WATCHER_CONTAINER $POSTGRES_CONTAINER 2>/dev/null || true

docker rmi ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} 2>/dev/null || true

./gradlew clean build

docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} .

docker-compose up -d