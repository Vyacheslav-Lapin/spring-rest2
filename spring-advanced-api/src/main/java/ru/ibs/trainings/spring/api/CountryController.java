package ru.ibs.trainings.spring.api;

import org.springframework.web.bind.annotation.GetMapping;
import ru.ibs.trainings.spring.dto.CountryDto;

import java.util.List;

public interface CountryController {

  @GetMapping("/countries")
  List<CountryDto> findAll();
}
