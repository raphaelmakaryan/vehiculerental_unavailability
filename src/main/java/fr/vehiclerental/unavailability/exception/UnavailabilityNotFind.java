package fr.vehiclerental.unavailability.exception;

public class UnavailabilityNotFind extends RuntimeException {
    public UnavailabilityNotFind() {
        super("The maintenance schedule defined for this vehicle was not found.");
    }
}
