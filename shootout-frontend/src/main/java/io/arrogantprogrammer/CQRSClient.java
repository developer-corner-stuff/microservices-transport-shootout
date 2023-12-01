package io.arrogantprogrammer;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/greetings")
@RegisterRestClient(configKey = "cqrs")
public interface CQRSClient {

    @POST
    public void addGreeting(GreetingJSON greetingJSON);

    @GET
    public List<GreetingJSON> allGreetings();
}
