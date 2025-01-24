package ru.ibs.trainings.spring.advanced.beans;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.ibs.trainings.spring.advanced.model.Country;
import ru.ibs.trainings.spring.advanced.model.Flight;
import ru.ibs.trainings.spring.advanced.model.Passenger;
import lombok.Cleanup;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static ru.ibs.trainings.spring.advanced.common.CsvUtils.*;

public class FlightBuilder {

    @Getter(onMethod_ = @Bean)
    final Map<String, Country> countriesMap;

    @SneakyThrows
    public FlightBuilder() {
        @Cleanup val countryMappingIterator = readFile("/countries_information.csv",
                                                                new TypeReference<Country>() {});
        countriesMap = countryMappingIterator.readAll().stream()
                                             .collect(toMap(Country::getCodeName, Function.identity()));
    }

    @Bean
    @SneakyThrows
    Flight buildFlightFromCsv() {
        val flight = new Flight("AA1234", 20);

        @Cleanup val passengerMappingIterator= readFile("/flights_information.csv");
        passengerMappingIterator.readAll().forEach(strings -> flight.addPassenger(
            new Passenger(strings.getFirst())
                .setCountry(countriesMap.get(strings.get(1).trim()))));

        return flight;
    }
}
