package io.arrogantprogrammer;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Greeting extends PanacheEntity {

    String text;

    boolean verified;

    public Greeting() {
    }

    public Greeting(String text) {
        this.text = text;
    }

    public Greeting(String text, boolean verified) {
        this.text = text;
        this.verified = verified;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isVerified() {
        return verified;
    }
}
