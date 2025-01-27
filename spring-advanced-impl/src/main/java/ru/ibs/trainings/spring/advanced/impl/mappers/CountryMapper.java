package ru.ibs.trainings.spring.advanced.impl.mappers;

import lombok.experimental.UtilityClass;
import ru.ibs.trainings.spring.advanced.impl.model.Country;
import ru.ibs.trainings.spring.dto.CountryDto;

@UtilityClass
public class CountryMapper {
  public CountryDto toCountryDto(Country country) {
    return CountryDto.builder()
                     .codeName(country.getCodeName())
                     .name(country.getName())
                     .build();
  }

  public Country toCountryEntity(CountryDto countryDto) {
    return new Country(countryDto.codeName(),
                       countryDto.name());
  }
}
