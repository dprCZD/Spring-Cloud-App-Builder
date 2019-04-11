#!/bin/bash

#unused

docker build -f /DockerSource/Dockerfile /DockerSource -t spring-cloud-app:0.0.1

docker login -u czd -p CHANGlast0803 172.16.1.99

docker tag  spring-cloud-app:0.0.1 172.16.1.99/tdctest

docker push 172.16.1.99/tdctest/tdc-spring-cloud-app:0.0.1

