package io.arrogantprogrammer;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class VerifiedGreetingResource {

    @Channel("greetings-familyfriendly")
    Emitter<GreetingJSON> familyFriendlyEmitter;

    static final Logger LOGGER = LoggerFactory.getLogger(VerifiedGreetingResource.class);
    @Incoming("greetings-verified") @Transactional @Blocking
    public void addGreeting(VerifiedGreeting verifiedGreetingJSON) {
        LOGGER.debug("received verified greeting: {}", verifiedGreetingJSON);
        Greeting greeting = new Greeting(verifiedGreetingJSON.text(), verifiedGreetingJSON.isFamilyFriendly());
        LOGGER.debug("persisting greeting: {}", greeting);
        familyFriendlyEmitter.send(new GreetingJSON(greeting.text));
        LOGGER.debug("sent family friendly greeting: {}", greeting.text);
    }
}
