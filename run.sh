#!/bin/bash
set -a
. .env
set +a

export VERSION=0.0.1
mvn clean
mvn install
#mvn spring-boot:run 
