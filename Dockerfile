FROM eclipse-temurin:17-jdk-jammy as builder

RUN mkdir /stock-ticker-service
WORKDIR /stock-ticker-service
COPY . .

RUN ./gradlew build jar

CMD java -jar build/libs/stock-ticker-service-0.0.1-SNAPSHOT.jar
