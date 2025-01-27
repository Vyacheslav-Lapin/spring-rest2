package ru.ibs.trainings.spring.dto;

import lombok.Builder;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Builder
@Jacksonized
public record FlightDto(
    String flightNumber,
    int seats,
    @Singular Set<PassengerDto> passengers) {
}
