FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
RUN groupadd --system --gid 10001 app \
    && useradd --system --uid 10001 --gid app --home-dir /app app \
    && mkdir -p /app/auto_ingest \
    && chown -R app:app /app
COPY --from=build --chown=app:app /workspace/target/software-development-document-environment-1.0.0-SNAPSHOT.jar /app/app.jar

USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]