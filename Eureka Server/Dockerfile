FROM openjdk:17
EXPOSE 8761
ADD target/EurekaServer.jar EurekaServer.jar
ENTRYPOINT [ "java","-jar","EurekaServer.jar" ]