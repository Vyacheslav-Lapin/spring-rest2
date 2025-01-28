package ru.ibs.trainings.spring.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

@Value
@Validated
@Jacksonized
@FieldNameConstants
@Accessors(fluent = true)
@Builder(toBuilder = true)
public class PassengerDto {
    @With @Default Long id = null;
    @Size(min = 2, max = 150) String name;
    CountryDto country;
    @Default boolean isRegistered = false;
}
