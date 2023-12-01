package io.arrogantprogrammer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GreetingService {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingService.class);
    @Inject
    @RestClient
    AIServiceClient aiServiceClient;

    @Transactional
    public void addGreeting(GreetingJSON greetingJSON) {

        if(aiServiceClient.isFamilyFriendly(greetingJSON)){
            Greeting greeting = new Greeting(greetingJSON.text());
            greeting.persist();
            LOGGER.debug("persisted greeting: {}", greetingJSON);
        }
    }
}
