package ru.ibs.trainings.spring.advanced.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.trainings.spring.advanced.controllers.api.PassengerController;
import ru.ibs.trainings.spring.advanced.dao.PassengerRepository;
import ru.ibs.trainings.spring.advanced.exceptions.PassengerNotFoundException;
import ru.ibs.trainings.spring.advanced.model.Country;
import ru.ibs.trainings.spring.advanced.model.Passenger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PassengerControllerImpl implements PassengerController {

  final PassengerRepository repository;
  final Map<String, Country> countriesMap;

  @Override
  public List<Passenger> findAll() {
    return repository.findAll();
  }

  @Override
  public Passenger createPassenger(Passenger passenger) {
    return repository.save(passenger);
  }

  @Override
  public Passenger findPassenger(Long id) {
    return repository.findById(id)
                     .orElseThrow(() -> new PassengerNotFoundException(id));
  }

  @Override
  public Passenger patchPassenger(Map<String, String> updates, Long id) {

    return repository.findById(id)
                     .map(passenger -> {

                       Optional.ofNullable(updates.get("name")).ifPresent(passenger::setName);

                       Optional.ofNullable(updates.get("country"))
                               .map(countriesMap::get)
                               .ifPresent(passenger::setCountry);

                       Optional.ofNullable(updates.get("isRegistered"))
                               .filter("true"::equalsIgnoreCase)
                               .ifPresent(__ -> passenger.setRegistered(true));

                       return repository.save(passenger);
                     })
                     .orElseThrow(() -> new PassengerNotFoundException(id));

  }
}
