package ru.ibs.trainings.spring.advanced;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ru.ibs.trainings.spring.advanced.beans.FlightBuilder;
import ru.ibs.trainings.spring.advanced.dao.CountryRepository;
import ru.ibs.trainings.spring.advanced.dao.PassengerRepository;
import ru.ibs.trainings.spring.advanced.model.Country;
import ru.ibs.trainings.spring.advanced.model.Flight;

import java.util.Map;

@SpringBootApplication
@RequiredArgsConstructor
@Import(FlightBuilder.class)
public class Application {

    final Flight flight;
    final Map<String, Country> countriesMap;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner configureRepository(CountryRepository countryRepository,
                                          PassengerRepository passengerRepository) {
        return args -> {
          countryRepository.saveAll(countriesMap.values());
          passengerRepository.saveAll(flight.getPassengers());
        };
    }
}
