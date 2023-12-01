package io.arrogantprogrammer;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/verify")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FamilyFriendlyAIResource {

    static final Logger LOGGER = LoggerFactory.getLogger(FamilyFriendlyAIResource.class);

    @Inject
    Verifier verifier;

    @GET
    public boolean verifyGreeting(GreetingJSON greetingJSON) {

        LOGGER.debug("verifyGreeting: {}", greetingJSON);
        return verifier.verifyText(greetingJSON.text());
    }
}
