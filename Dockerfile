#base image: linux alpine os with open jdk 17
#Dockerfile - cmd vao folder chua Dockerfile
FROM eclipse-temurin:17-jre-alpine
#copy jar from local into docker image
COPY target/shop-0.0.1-SNAPSHOT.jar shop-0.0.1-SNAPSHOT.jar
#command line to run jar
ENTRYPOINT ["java","-jar","/shop-0.0.1-SNAPSHOT.jar"]