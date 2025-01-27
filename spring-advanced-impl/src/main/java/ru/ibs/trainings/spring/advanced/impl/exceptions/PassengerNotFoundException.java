package ru.ibs.trainings.spring.advanced.impl.exceptions;

public class PassengerNotFoundException extends RuntimeException {

    public PassengerNotFoundException(Long id) {
        super("Passenger id not found : " + id);
    }

}
