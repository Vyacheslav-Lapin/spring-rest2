package ru.ibs.trainings.spring.advanced.impl.controllers.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.trainings.spring.advanced.impl.dao.PassengerRepository;
import ru.ibs.trainings.spring.advanced.impl.exceptions.PassengerNotFoundException;
import ru.ibs.trainings.spring.advanced.impl.mappers.CountryMapper;
import ru.ibs.trainings.spring.advanced.impl.mappers.PassengerMapper;
import ru.ibs.trainings.spring.api.PassengerController;
import ru.ibs.trainings.spring.dto.CountryDto;
import ru.ibs.trainings.spring.dto.PassengerDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@ExtensionMethod(value = PassengerMapper.class, suppressBaseMethods = false)
public class PassengerControllerImpl implements PassengerController {

  PassengerRepository repository;
  Map<String, CountryDto> countriesMap;

  @Override
  public ResponseEntity<List<PassengerDto>> findAll() {
    val all = repository.findAll().stream()
                        .map(passenger -> PassengerDto.builder()
                                                      .id(passenger.getId())
                                                      .name(passenger.getName())
                                                      .isRegistered(passenger.isRegistered())
                                                      .country(CountryDto.builder()
                                                                         .name(passenger.getCountry().getName())
                                                                         .codeName(passenger.getCountry().getCodeName()).build()).build())
                        .toList();
    return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
  }

  @Override
  public ResponseEntity<PassengerDto> createPassenger(PassengerDto passenger, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().build();
    }
    val passengerDto = repository.save(passenger.toPassengerEntity())
                                          .toPassengerDto();
    return ResponseEntity.ok(passengerDto);
  }

  @Override
  public PassengerDto findPassenger(Long id) {
    return repository.findById(id)
                     .map(PassengerMapper::toPassengerDto)
                     .orElseThrow(() -> new PassengerNotFoundException(id));
  }

  @Override
  public PassengerDto patchPassenger(Map<String, String> updates, Long id) {

    return repository.findById(id)
                     .map(passenger -> {

                       Optional.ofNullable(updates.get("name")).ifPresent(passenger::setName);

                       Optional.ofNullable(updates.get("country"))
                               .map(countriesMap::get)
                               .map(CountryMapper::toCountryEntity)
                               .ifPresent(passenger::setCountry);

                       Optional.ofNullable(updates.get("isRegistered"))
                               .filter("true"::equalsIgnoreCase)
                               .ifPresent(__ -> passenger.setRegistered(true));

                       return repository.save(passenger).toPassengerDto();
                     })
                     .orElseThrow(() -> new PassengerNotFoundException(id));

  }
}
