#!/bin/bash
set -a
. .env
set +a


export VERSION=0.0.1
mvn clean

if [ $1 = 'build' ]
then
  mvn install
elif [ $1 = 'run' ]
then
  mvn spring-boot:run
fi

