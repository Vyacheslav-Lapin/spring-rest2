package ru.ibs.trainings.spring.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

@Value
@Builder
@Validated
@Jacksonized
@Accessors(fluent = true)
public class PassengerDto {
    @Default Long id = null;
    @Size(min = 2, max = 150) String name;
    CountryDto country;
    @Default boolean isRegistered = false;
}
