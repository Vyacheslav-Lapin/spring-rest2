package ru.ibs.trainings.spring.advanced.impl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.*;
import static ru.ibs.trainings.spring.advanced.impl.common.HibernateUtils.*;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = PROTECTED)// avoid "No default constructor for entity"
@SuppressWarnings({"com.haulmont.ampjpb.LombokDataInspection", "com.intellij.jpb.LombokDataInspection"})
public class Passenger {

    @Id @GeneratedValue Long id;
    @NonNull String name;
    @ManyToOne Country country;
    boolean isRegistered;

    @Override
    public final boolean equals(Object o) {
      return this == o || o instanceof Passenger passenger
                          && effectiveClass(this) == effectiveClass(passenger)
                                            && getId() != null
                                            && Objects.equals(getId(), passenger.getId());
    }

    @Override
    public final int hashCode() {
        return effectiveClass(this).hashCode();
    }
}
