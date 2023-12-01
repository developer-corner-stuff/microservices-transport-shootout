package io.arrogantprogrammer;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Greeting extends PanacheEntity {

    String text;

    boolean isFamilyFriendly;

    public Greeting() {
    }

    public Greeting(String text) {
        this.text = text;
    }

    public Greeting(String text, boolean isFamilyFriendly){
        this.text = text;
        this.isFamilyFriendly = isFamilyFriendly;
    }

}
