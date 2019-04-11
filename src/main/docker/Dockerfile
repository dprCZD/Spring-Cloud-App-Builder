FROM 172.16.1.99/transwarp/tdc-base-image:latest
VOLUME /tmp
ADD source/app-Dockerfile /DockerSource/Dockerfile
ADD spring-cloud-app-builder-1.0-SNAPSHOT.jar spring-cloud-app-builder-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-cloud-app-builder-1.0-SNAPSHOT.jar"]
