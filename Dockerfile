FROM openjdk:8
EXPOSE 9090
ADD target/sale-sport.jar sale-sport.jar
ENTRYPOINT ["java","-jar","/sale-sport.jar"]
