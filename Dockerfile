FROM openjdk:11-oracle
COPY "./target/credit-service-1.0.0.jar" "/app/credit-service-1.0.0.jar"
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/credit-service-1.0.0.jar"]