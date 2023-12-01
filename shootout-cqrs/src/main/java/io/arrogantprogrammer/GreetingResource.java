package io.arrogantprogrammer;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/greetings")
public class GreetingResource {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);

    @POST
    @Transactional
    public void addGreeting(GreetingJSON greetingJSON) {
        LOGGER.debug("addGreeting: {}", greetingJSON);
        Greeting greeting = new Greeting(greetingJSON.text());
        greeting.persist();
        LOGGER.debug("addGreeting persisted: {}", greeting);
    }

    @GET
    public List<GreetingJSON> listGreetings() {
        LOGGER.debug("listGreetings");
        List<Greeting> allGreetings = Greeting.listAll();
        LOGGER.debug("listGreetings found {} greetings", allGreetings.size());
        List<GreetingJSON> greetings = allGreetings.stream().map(greeting -> new GreetingJSON(greeting.text)).toList();
        LOGGER.debug("listGreetings returning {} greetings", greetings.size());
        return greetings;
    }
}
