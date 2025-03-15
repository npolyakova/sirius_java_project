FROM gradle:8.13.0-jdk21 AS build
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN gradle clean build -x test

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/moule1-0.0.1-SNAPSHOT.jar ./my-app.jar
EXPOSE 8080

CMD ["java", "-jar", "my-app.jar"]