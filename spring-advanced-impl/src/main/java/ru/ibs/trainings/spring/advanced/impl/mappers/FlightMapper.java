package ru.ibs.trainings.spring.advanced.impl.mappers;

import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;
import ru.ibs.trainings.spring.advanced.impl.model.Flight;
import ru.ibs.trainings.spring.dto.FlightDto;

@UtilityClass
public class FlightMapper {
  public FlightDto toFlightDto(@NonNull Flight flight) {
    return FlightDto.builder()
                    .flightNumber(flight.getFlightNumber())
                    .seats(flight.getSeats())
                    .passengers(flight.getPassengers().stream()
                                      .map(PassengerMapper::toPassengerDto)
                                      .toList()).build();
  }

  public Flight toFlightEntity(@NonNull FlightDto flightDto) {
    return new Flight(flightDto.flightNumber(), flightDto.seats())
        .addAllPassengers(flightDto.passengers().stream()
                              .map(PassengerMapper::toPassengerEntity)
                              .toList());
  }
}
