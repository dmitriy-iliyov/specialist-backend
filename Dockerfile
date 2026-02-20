FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY ../backend/pom.xml .
COPY .. .
RUN mvn clean package -pl app -am -DskipTests

FROM eclipse-temurin:21-jre AS final
WORKDIR /app
COPY --from=build /app/app/target/*.jar app.jar
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD []