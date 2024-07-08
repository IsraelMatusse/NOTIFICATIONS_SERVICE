FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app/
RUN env

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:resolve
COPY src ./src

EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]