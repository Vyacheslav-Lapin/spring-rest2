package com.luxoft.springadvanced.springrest.model;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryController {

    final CountryRepository repository;

    @GetMapping("/countries")
    List<Country> findAll() {
        return repository.findAll();
    }

}
