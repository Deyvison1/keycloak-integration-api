FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew build -Dquarkus.package.jar.enabled=true --no-daemon -x test

FROM eclipse-temurin:21-jre

WORKDIR /app

ENV TZ=America/Sao_Paulo
ENV QUARKUS_PROFILE=docker

COPY --from=builder /app/build/quarkus-app/ ./

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]