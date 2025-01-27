package ru.ibs.trainings.spring.advanced.impl.mappers;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import ru.ibs.trainings.spring.advanced.impl.model.Passenger;
import ru.ibs.trainings.spring.dto.PassengerDto;

@UtilityClass
@ExtensionMethod(value = CountryMapper.class, suppressBaseMethods =false)
public class PassengerMapper {
  public Passenger toPassengerEntity(PassengerDto passengerDto) {
    return new Passenger(passengerDto.name())
        .setId(passengerDto.id())
        .setRegistered(passengerDto.isRegistered())
        .setCountry(CountryMapper.toCountryEntity(passengerDto.country()));
  }

  public PassengerDto toPassengerDto(Passenger passenger) {
    return PassengerDto.builder()
                       .id(passenger.getId())
                       .name(passenger.getName())
                       .country(passenger.getCountry().toCountryDto())
                       .isRegistered(passenger.isRegistered())
                       .build();
  }
}
