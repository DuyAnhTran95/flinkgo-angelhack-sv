FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/
ARG VERSION
COPY ${DEPENDENCY}/flinkgo-sv-${VERSION}.jar /app/flinkgo-sv.jar
ENTRYPOINT ["java","-jar","/app/flinkgo-sv.jar"]
EXPOSE 8080