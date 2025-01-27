package ru.ibs.trainings.spring.advanced.impl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ibs.trainings.spring.advanced.impl.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
