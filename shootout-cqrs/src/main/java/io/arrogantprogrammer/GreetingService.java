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

        VerifiedGreetingJSON verifiedGreetingJSON = aiServiceClient.isFamilyFriendly(greetingJSON);
        if(verifiedGreetingJSON.isFamilyFriendly()){
            Greeting greeting = new Greeting(greetingJSON.text(), true);
            greeting.persist();
            LOGGER.debug("persisted family friendly greeting: {}", greetingJSON);
        }else{
            Greeting greeting = new Greeting(greetingJSON.text(), false);
            greeting.persist();
            LOGGER.debug("persisted non family friendly greeting: {}", greetingJSON);
        }
    }
}
