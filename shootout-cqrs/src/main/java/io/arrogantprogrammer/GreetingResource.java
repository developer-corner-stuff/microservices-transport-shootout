package io.arrogantprogrammer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GreetingResource {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);

    @Channel("greetings-verifications")
    Emitter<GreetingJSON> greetingVerificationsEmitter;

    @Inject
    GreetingService greetingService;

    @Incoming("greetings-submissions")
    public void verifyGreeting(GreetingJSON greetingJSON) {
        LOGGER.debug("greeting submitted: {}", greetingJSON);
        greetingVerificationsEmitter.send(greetingJSON);
    }

}
