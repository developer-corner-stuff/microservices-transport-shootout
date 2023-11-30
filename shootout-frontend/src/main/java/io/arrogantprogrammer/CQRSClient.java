package io.arrogantprogrammer;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/greetings")
@RegisterRestClient(configKey = "cqrs")
public interface CQRSClient {

    @POST
    public Response addGreeting(GreetingJSON greetingJSON);

}
