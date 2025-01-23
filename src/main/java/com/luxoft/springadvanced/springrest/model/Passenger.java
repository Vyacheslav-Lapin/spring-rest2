package com.luxoft.springadvanced.springrest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static lombok.AccessLevel.*;

@Data
@Entity
@NoArgsConstructor(access = PROTECTED)// avoid "No default constructor for entity"
public class Passenger {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    private Country country;
    private boolean isRegistered;

    public Passenger(String name) {
        this.name = name;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    @Override
    @SuppressWarnings("java:S2097")
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Passenger passenger = (Passenger) o;
        return getId() != null && Objects.equals(getId(), passenger.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
