package io.arrogantprogrammer;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GreetingService {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingService.class);
    @Inject
    AIServiceClient aiServiceClient;

    @Transactional
    public Uni<Void> addGreeting(GreetingJSON greetingJSON) {

//        LOGGER.debug("adding greeting: {}", greetingJSON);
//
//        Uni.createFrom().item(aiServiceClient.isFamilyFriendly(greetingJSON))
//                .onItem()
//                .transform(isFamilyFriendly -> {
//                    if(isFamilyFriendly){
//                        LOGGER.debug("verified greeting: {}", greetingJSON);
//                        Greeting greeting = new Greeting(greetingJSON.text());
//                        greeting.persist();
//                        LOGGER.debug("persisted greeting: {}", greetingJSON);
//                    }
//                return isFamilyFriendly;
//        }).subscribe();
        return null;
    }
}
