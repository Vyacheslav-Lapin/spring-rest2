package ru.ibs.trainings.spring.advanced.dao;

import ru.ibs.trainings.spring.advanced.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
