package com.luxoft.springadvanced.springrest.beans;

import com.luxoft.springadvanced.springrest.model.Country;
import com.luxoft.springadvanced.springrest.model.Flight;
import com.luxoft.springadvanced.springrest.model.Passenger;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FlightBuilder {

    private Map<String, Country> countriesMap = new HashMap<>();

    public FlightBuilder() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/countries_information.csv"))) {
            String line = null;
            do {
                line = reader.readLine();
                if (line != null) {
                    String[] countriesString = line.toString().split(";");
                    Country country = new Country(countriesString[0].trim(), countriesString[1].trim());
                    countriesMap.put(countriesString[1].trim(), country);
                }
            } while (line != null);
        }
    }

    @Bean
    Map<String, Country> getCountriesMap() {
        return Collections.unmodifiableMap(countriesMap);
    }

    @Bean
    public Flight buildFlightFromCsv() throws IOException {
        Flight flight = new Flight("AA1234", 20);
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/flights_information.csv"))) {
            String line = null;
            do {
                line = reader.readLine();
                if (line != null) {
                    String[] passengerString = line.toString().split(";");
                    Passenger passenger = new Passenger(passengerString[0].trim());
                    passenger.setCountry(countriesMap.get(passengerString[1].trim()));
                    passenger.setRegistered(false);
                    flight.addPassenger(passenger);
                }
            } while (line != null);

        }

        return flight;
    }
}
