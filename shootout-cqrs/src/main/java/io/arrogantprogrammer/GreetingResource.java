package io.arrogantprogrammer;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/greetings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);

    @Inject
    GreetingService greetingService;

    @POST
    @Transactional
    public void addGreeting(GreetingJSON greetingJSON) {
        LOGGER.debug("addGreeting: {}", greetingJSON);
        greetingService.addGreeting(greetingJSON);
    }

    @GET
    public List<VerifiedGreetingJSON> listGreetings() {
        LOGGER.debug("listGreetings");
        List<Greeting> allGreetings = Greeting.listAll();
        LOGGER.debug("listGreetings found {} greetings", allGreetings.size());
        List<VerifiedGreetingJSON> greetings = allGreetings.stream().map(greeting -> new VerifiedGreetingJSON(greeting.text, greeting.isFamilyFriendly)).toList();
        LOGGER.debug("listGreetings returning {} greetings", greetings.size());
        return greetings;
    }
}
