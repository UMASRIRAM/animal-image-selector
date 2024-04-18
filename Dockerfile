FROM openjdk:17
EXPOSE 8080
VOLUME /tmp

ADD target/animal-image-selector-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]