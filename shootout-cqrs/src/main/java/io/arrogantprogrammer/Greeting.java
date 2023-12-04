package io.arrogantprogrammer;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.Instant;

@Entity
public class Greeting extends PanacheEntity {

    String text;

    boolean isFamilyFriendly;

    Instant createdAt = Instant.now();

    public Greeting() {
    }

    public Greeting(String text, boolean isFamilyFriendly){
        this.text = text;
        this.isFamilyFriendly = isFamilyFriendly;
    }

    public String getText() {
        return text;
    }

    public boolean isFamilyFriendly() {
        return isFamilyFriendly;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
