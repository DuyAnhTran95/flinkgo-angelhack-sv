FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/
ARG VERSION
COPY ${DEPENDENCY}/flinkgo-${VERSION}.jar /app/flinkgo.jar
ENTRYPOINT ["java","-jar","/app/flinkgo.jar"]
EXPOSE 8080