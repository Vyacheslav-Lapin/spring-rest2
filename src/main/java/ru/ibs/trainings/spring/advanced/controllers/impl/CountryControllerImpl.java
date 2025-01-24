package ru.ibs.trainings.spring.advanced.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.trainings.spring.advanced.controllers.api.CountryController;
import ru.ibs.trainings.spring.advanced.dao.CountryRepository;
import ru.ibs.trainings.spring.advanced.model.Country;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryControllerImpl implements CountryController {

    final CountryRepository repository;

    @Override
    public List<Country> findAll() {
        return repository.findAll();
    }

}
