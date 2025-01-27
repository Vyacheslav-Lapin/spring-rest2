package ru.ibs.trainings.spring.advanced.impl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ibs.trainings.spring.advanced.impl.model.Country;

public interface CountryRepository extends JpaRepository<Country, String> {
}
