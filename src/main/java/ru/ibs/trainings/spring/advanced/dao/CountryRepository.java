package ru.ibs.trainings.spring.advanced.dao;

import ru.ibs.trainings.spring.advanced.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
