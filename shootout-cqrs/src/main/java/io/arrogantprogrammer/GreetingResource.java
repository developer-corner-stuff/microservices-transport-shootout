package io.arrogantprogrammer;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/greetings")
public class GreetingResource {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);

    @POST
    @Transactional
    public Response addGreeting(GreetingJSON greetingJSON) {
        LOGGER.debug("addGreeting: {}", greetingJSON);
        Greeting greeting = new Greeting(greetingJSON.text());
        greeting.persist();
        return Response.ok("Hello from RESTEasy Reactive").build();
    }
}
