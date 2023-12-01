package io.arrogantprogrammer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Path("/hello")
public class GreetingResource {
    
    @Inject
    @RestClient
    CQRSClient cqrsClient;

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
        cqrsClient.addGreeting(greetingJSON);
        return Response.accepted().build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allGreetings() {
        LOGGER.debug("allGreetings");
        List<GreetingJSON> greetings = cqrsClient.allGreetings();
        LOGGER.debug("response: {}", greetings);
        return Response.ok().entity(greetings).build();
    }

}
