## Alpine because it's lighter
FROM gradle:7.6.0-jdk17-alpine AS build
#ARG version
#ARG sourceCompatibility
#ARG springBootVersion
#ARG springDependencyManagementVersion
#ARG springCloudVersion
#ENV TZ=Europe/Kiev
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build
#RUN gradle build  \
#    -Pversion=${version} \
#    -PsourceCompatibility=${sourceCompatibility} \
#    -PspringBootVersion=${springBootVersion} \
#    -PspringDependencyManagementVersion=${springDependencyManagementVersion} \
#    -PspringCloudVersion=${springCloudVersion} \
#    --no-daemon

FROM openjdk:17-slim

EXPOSE 8091

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar","/app/app.jar"]
