package com.luxoft.springadvanced.springrest.exceptions;

public class PassengerNotFoundException extends RuntimeException {

    public PassengerNotFoundException(Long id) {
        super("Passenger id not found : " + id);
    }

}
