package ru.ibs.trainings.spring.dto;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Accessors(fluent = true)
public class PassengerDto {
    @Default Long id = null;
    String name;
    CountryDto country;
    @Default boolean isRegistered = false;
}
