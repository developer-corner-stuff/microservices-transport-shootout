package io.arrogantprogrammer;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/verify")
@RegisterRestClient(configKey = "ai-service")
public interface AIServiceClient {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    VerifiedGreetingJSON isFamilyFriendly(GreetingJSON greetingJSON);
}
