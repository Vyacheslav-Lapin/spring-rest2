package ru.ibs.trainings.spring.advanced.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ru.ibs.trainings.spring.advanced.impl.beans.FlightConfig;
import ru.ibs.trainings.spring.advanced.impl.dao.CountryRepository;
import ru.ibs.trainings.spring.advanced.impl.dao.PassengerRepository;
import ru.ibs.trainings.spring.advanced.impl.model.Country;
import ru.ibs.trainings.spring.advanced.impl.model.Passenger;
import ru.ibs.trainings.spring.dto.CountryDto;
import ru.ibs.trainings.spring.dto.FlightDto;

import java.util.Map;

@SpringBootApplication
@RequiredArgsConstructor
@Import(FlightConfig.class)
public class Application {

    FlightDto flightDto;
    Map<String, CountryDto> countriesMap;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner configureRepository(CountryRepository countryRepository,
                                          PassengerRepository passengerRepository) {
        return args -> {
          countryRepository.saveAll(countriesMap.values().stream()
                                                .map(countryDto -> new Country(countryDto.codeName(), countryDto.name()))
                                                .toList());
          passengerRepository.saveAll(flightDto.passengers().stream()
                                          .map(passengerDto -> new Passenger(passengerDto.name())
                                              .setRegistered(passengerDto.isRegistered())
                                              .setCountry(new Country(passengerDto.country().codeName(),
                                                                      passengerDto.country().name())))
                                          .toList());
        };
    }
}
