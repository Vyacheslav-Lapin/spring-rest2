package com.luxoft.springadvanced.springrest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.*;
import static ru.ibs.trainings.spring.advanced.common.HibernateUtils.*;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = PROTECTED) // avoid "No default constructor for entity"
@SuppressWarnings({"com.intellij.jpb.LombokDataInspection", "com.haulmont.ampjpb.LombokDataInspection"})
public class Country {

    @Id
    @NonNull String codeName;
    @NonNull String name;

    @Override
    public final boolean equals(Object o) {
      return this == o || o instanceof Country country
                          && effectiveClass(this) == effectiveClass(country)
                          && getCodeName() != null
                          && Objects.equals(getCodeName(), country.getCodeName());
    }

    @Override
    public final int hashCode() {
        return effectiveClass(this).hashCode();
    }
}
