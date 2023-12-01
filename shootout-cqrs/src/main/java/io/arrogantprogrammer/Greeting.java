package io.arrogantprogrammer;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Greeting extends PanacheEntity {

    String text;

    public Greeting() {
    }

    public Greeting(String text) {
        this.text = text;
    }


}
