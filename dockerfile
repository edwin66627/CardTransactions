FROM openjdk:11
VOLUME /tmp
COPY target/CardTransactions-0.0.1-SNAPSHOT.jar CardTransactions.jar
ENTRYPOINT ["java", "-jar", "/CardTransactions.jar"]

