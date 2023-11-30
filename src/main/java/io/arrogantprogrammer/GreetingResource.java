package io.arrogantprogrammer;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/hello")
public class GreetingResource {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGreeting(GreetingJSON greetingJSON) {

        LOGGER.debug("received: {}", greetingJSON);
        return Response.accepted().build();
    }

}
