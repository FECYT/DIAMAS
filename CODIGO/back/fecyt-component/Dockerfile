FROM maven:3.5.2-jdk-8-alpine as fase1

#Compilar la aplicacion
WORKDIR /usr/src/app

COPY . .

ARG ip_artifactory
RUN mvn clean package -s .m2/settings.xml -P docker -Dmaven.test.skip=true

FROM openjdk:8-alpine

#Para poder implementar el servicio de health en el docker compose
RUN apk --no-cache add curl

WORKDIR /usr/src/app

COPY --from=fase1 /usr/src/app/target/verificaciontitulos-component-rest-*.jar ./verificaciontitulos-component-rest.jar

EXPOSE 8761

CMD ["java", "-jar", "verificaciontitulos-component-rest.jar"]
