package io.arrogantprogrammer;

import io.arrogantprogrammer.proto.GreetingProto;
import io.arrogantprogrammer.proto.ProtoGreetingService;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@GrpcService
public class GreetingResource implements ProtoGreetingService {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);

    @Inject
    GreetingService greetingService;

    public List<GreetingJSON> listGreetings() {
        LOGGER.debug("listGreetings");
        List<Greeting> allGreetings = Greeting.listAll();
        LOGGER.debug("listGreetings found {} greetings", allGreetings.size());
        List<GreetingJSON> greetings = allGreetings.stream().map(greeting -> new GreetingJSON(greeting.text)).toList();
        LOGGER.debug("listGreetings returning {} greetings", greetings.size());
        return greetings;
    }

    @Override
    public Uni<GreetingProto> addGreeting(GreetingProto request) {
        LOGGER.debug("adding : {}", request);
        return Uni.createFrom().item(request);
    }
}
