FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw

COPY src src

RUN ./mvnw clean package -DskipTests

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "target/Evaluacion2-0.0.1-SNAPSHOT.jar"]