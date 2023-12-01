package io.arrogantprogrammer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Path("/hello")
public class GreetingResource {
    
    @Inject
    GreetingRepository greetingRepository;

    @Channel("greetings-out")
    Emitter<GreetingJSON> emitter;

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
        emitter.send(greetingJSON);
        return Response.accepted().build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allGreetings() {
        LOGGER.debug("allGreetings");
        return Response.ok().entity(greetingRepository.allGreetings()).build();
    }

}
