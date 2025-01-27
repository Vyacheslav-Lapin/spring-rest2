package ru.ibs.trainings.spring.advanced.impl.beans;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ibs.trainings.spring.dto.CountryDto;
import ru.ibs.trainings.spring.dto.FlightDto;
import ru.ibs.trainings.spring.dto.PassengerDto;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static ru.ibs.trainings.spring.advanced.impl.common.CsvUtils.*;

@Configuration
public class FlightConfig {

    Map<String, CountryDto> countriesMap;

    @SneakyThrows
    public FlightConfig() {
        @Cleanup val countryMappingIterator = readFile("/countries_information.csv",
                                                       new TypeReference<CountryDto>() {});
        countriesMap = countryMappingIterator.readAll().stream()
                                             .collect(toMap(CountryDto::codeName, Function.identity()));
    }

    @Bean
    @SneakyThrows
    FlightDto buildFlightFromCsv() {
        val flightDtoBuilder = FlightDto.builder()
                                        .flightNumber("AA1234")
                                        .seats(20);

        @Cleanup val passengerMappingIterator = readFile("/flights_information.csv");
        passengerMappingIterator.readAll().stream()
                                .map(strings -> PassengerDto.builder()
                                                            .name(strings.getFirst())
                                                            .country(countriesMap.get(strings.get(1).trim())).build())
                                .forEach(flightDtoBuilder::passenger);

        return flightDtoBuilder.build();
    }

    @Bean
    Map<String, CountryDto> getCountriesMap() {
        return this.countriesMap;
    }
}
