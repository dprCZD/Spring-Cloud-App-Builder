FROM 172.16.1.99/transwarp/tdc-base-image:latest
VOLUME /tmp
ADD spring-cloud-app-0.0.1.jar spring-cloud-app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-cloud-app.jar"]