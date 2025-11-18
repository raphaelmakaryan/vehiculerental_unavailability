FROM openjdk:26-ea-slim-trixie
WORKDIR /app
COPY ./target/vehicleRentalUnavailability-0.0.1-SNAPSHOT.jar /app/vehicleRentalUnavailability-0.0.1-SNAPSHOT.jar
EXPOSE 8085
CMD ["java", "-jar", "/app/vehicleRentalUnavailability-0.0.1-SNAPSHOT.jar"]