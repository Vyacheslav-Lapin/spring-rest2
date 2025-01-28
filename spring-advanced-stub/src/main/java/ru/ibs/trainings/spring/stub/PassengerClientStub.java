package ru.ibs.trainings.spring.stub;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.val;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.ibs.trainings.spring.api.CountryController;
import ru.ibs.trainings.spring.api.PassengerController;
import ru.ibs.trainings.spring.dto.CountryDto;
import ru.ibs.trainings.spring.dto.PassengerDto;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;
import static ru.ibs.trainings.spring.dto.PassengerDto.Fields.*;
import static ru.ibs.trainings.spring.stub.common.CsvUtils.*;


@Primary
@Component
@RequiredArgsConstructor
@Accessors(fluent = true)
public class PassengerClientStub implements PassengerController {

  CountryController countryController;

  @Getter(lazy = true, value = AccessLevel.PRIVATE)
  Map<String, CountryDto> countryMap = countryController.findAll().stream()
      .collect(toUnmodifiableMap(CountryDto::codeName, identity()));

  @Getter(lazy = true, value = AccessLevel.PRIVATE)
  Map<Long, PassengerDto> passengers = readPassengers();

  private static final AtomicLong atomicLong = new AtomicLong();

  @SneakyThrows
  private Map<Long, PassengerDto> readPassengers() {
    @Cleanup val passengerMappingIterator = readFile("/flights_information.csv");
    return passengerMappingIterator.readAll().stream()
                                   .map(strings -> PassengerDto.builder()
                                                               .id(atomicLong.incrementAndGet())
                                                               .name(strings.getFirst())
                                                               .country(countryMap().get(strings.get(1).trim())).build())
                                   .collect(toMap(PassengerDto::id, identity()));
  }

  @Override
  public ResponseEntity<List<PassengerDto>> findAll() {
    return ResponseEntity.ok(passengers().values().stream().toList());
  }

  @Override
  public ResponseEntity<PassengerDto> createPassenger(PassengerDto passenger, Errors errors) {
    long id = atomicLong.incrementAndGet();
    passengers.put(id, passenger.withId(id));
    return ResponseEntity.created(
        URI.create("/passengers/" + id))
                         .build();
  }

  @Override
  public PassengerDto findPassenger(Long id) {
    return passengers().get(id);
  }

  @Override
  public PassengerDto patchPassenger(Map<String, String> updates, Long id) {
    val builder = passengers().get(id).toBuilder();

    if (updates.containsKey(NAME))
      builder.name(updates.get(NAME));

    if (updates.containsKey(IS_REGISTERED))
      builder.isRegistered(updates.get(IS_REGISTERED).equals("true"));

    if (updates.containsKey(COUNTRY))
      builder.country(countryMap.get(updates.get(COUNTRY)));

    val dto = builder.build();

    passengers().put(id, dto);

    return dto;
  }
}
