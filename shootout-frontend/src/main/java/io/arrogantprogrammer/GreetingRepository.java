package io.arrogantprogrammer;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GreetingRepository {

    List<GreetingJSON> allGreetings = new ArrayList<>();
    @Incoming("greetings-in")
    public void addGreeting(GreetingJSON greetingJSON) {
        System.out.println("GreetingRepository.addGreeting: " + greetingJSON);
        allGreetings.add(greetingJSON);
    }

    public List<GreetingJSON> allGreetings() {
        return allGreetings;
    }
}
