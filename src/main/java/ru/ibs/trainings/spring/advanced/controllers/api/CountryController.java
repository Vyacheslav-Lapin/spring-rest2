package ru.ibs.trainings.spring.advanced.controllers.api;

import org.springframework.web.bind.annotation.GetMapping;
import ru.ibs.trainings.spring.advanced.model.Country;

import java.util.List;

public interface CountryController {

  @GetMapping("/countries")
  List<Country> findAll();
}
