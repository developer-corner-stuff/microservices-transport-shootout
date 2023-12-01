package io.arrogantprogrammer;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class FamilyFriendlyAIResource {

    static final Logger LOGGER = LoggerFactory.getLogger(FamilyFriendlyAIResource.class);

    @Inject
    Verifier verifier;

    @Channel("greetings-verified")
    Emitter<VerifiedGreetingJSON> verifiedGreetingsEmitter;

    @Incoming("greetings-verifications")
    public void verifyGreeting(UnverifiedGreetingJSON unverifiedGreetingJSON) {

        LOGGER.debug("verifying unverifiedGreeting: {}", unverifiedGreetingJSON);
        verifier.verifyText(unverifiedGreetingJSON.text()).onItem().transform(isFamilyFriendly -> {
            LOGGER.debug("isFamilyFriendly: {}", isFamilyFriendly);
            return isFamilyFriendly;
        }).subscribe().with(isFamilyFriendly -> {
            verifiedGreetingsEmitter.send(new VerifiedGreetingJSON(unverifiedGreetingJSON.text(), isFamilyFriendly));
            LOGGER.debug("sent: {}", unverifiedGreetingJSON);
        });
    }
}
