package ru.ibs.trainings.spring.advanced.model;

import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Value
@ToString(onlyExplicitlyIncluded = true)
public class Flight {

    @ToString.Include String flightNumber;
    int seats;
    Set<Passenger> passengers = new HashSet<>();

    public Set<Passenger> getPassengers() {
        return Collections.unmodifiableSet(passengers);
    }

    public void addPassenger(Passenger passenger) {
        if (passengers.size() >= seats)
            throw new RuntimeException("Cannot add more passengers than the capacity of the flight!");
        passengers.add(passenger);
    }

    public boolean removePassenger(Passenger passenger) {
        return passengers.remove(passenger);
    }
}
