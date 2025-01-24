package ru.ibs.trainings.spring.advanced.controllers.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ibs.trainings.spring.advanced.model.Passenger;

import java.util.List;
import java.util.Map;

public interface PassengerController {

  @GetMapping("/passengers")
  List<Passenger> findAll();

  @PostMapping("/passengers")
  @ResponseStatus(HttpStatus.CREATED)
  Passenger createPassenger(@RequestBody Passenger passenger);

  @GetMapping("/passengers/{id}")
  Passenger findPassenger(@PathVariable Long id);

  @PatchMapping("/passengers/{id}")
  Passenger patchPassenger(@RequestBody Map<String, String> updates, @PathVariable Long id);
}
