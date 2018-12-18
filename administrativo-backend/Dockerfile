FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/lenincamp/DesafioFullStack.git

#FROM maven:3.6.0-jdk-8 as build
#WORKDIR /app
#COPY --from=clone /app/DesafioFullStack/administrativo-backend /app
#RUN mvn install

FROM openjdk:8
WORKDIR /app
ADD administrativo-ws/target/administrativo-ws-0.0.1-SNAPSHOT.jar /app

EXPOSE 8081
ENTRYPOINT ["java","-jar", "administrativo-ws-0.0.1-SNAPSHOT.jar"]
