FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY . .
RUN mvn clean package -pl app -am -DskipTests

FROM jbr-17.0.11 AS final
WORKDIR /app
COPY --from=build /app/app/target/*.jar app.jar
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD []