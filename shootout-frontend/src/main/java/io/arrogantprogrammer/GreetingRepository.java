package io.arrogantprogrammer;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GreetingRepository {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingRepository.class);

    List<GreetingJSON> allGreetings = new ArrayList<>();
    @Incoming("greetings-in")
    public void addGreeting(GreetingJSON greetingJSON) {
        LOGGER.debug("GreetingRepository adding: {}", greetingJSON);
        allGreetings.add(greetingJSON);
        LOGGER.debug("now there are {} greetings", allGreetings.size());
    }

    public List<GreetingJSON> allGreetings() {
        return allGreetings;
    }
}
