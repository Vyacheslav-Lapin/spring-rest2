package ru.ibs.trainings.spring.advanced.impl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.trainings.spring.advanced.impl.dao.CountryRepository;
import ru.ibs.trainings.spring.api.CountryController;
import ru.ibs.trainings.spring.dto.CountryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryControllerImpl implements CountryController {

  CountryRepository repository;

  @Override
  public List<CountryDto> findAll() {
    return repository.findAll().stream()
        .map(country -> CountryDto.builder()
            .codeName(country.getCodeName())
            .name(country.getName()).build())
        .toList();
  }
}
