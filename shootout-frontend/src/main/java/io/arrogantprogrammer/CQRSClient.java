package io.arrogantprogrammer;

import io.arrogantprogrammer.proto.GreetingProto;
import io.arrogantprogrammer.proto.ProtoGreetingService;
import io.quarkus.grpc.GrpcClient;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CQRSClient {

    static final Logger LOGGER = LoggerFactory.getLogger(CQRSClient.class);
    @GrpcClient("cqrs")
    ProtoGreetingService protoGreetingService;

    public void addGreeting(GreetingJSON greetingJSON) {

        LOGGER.debug("adding greeting: {}", greetingJSON);
        GreetingProto greetingProto = GreetingProto.newBuilder().setText(greetingJSON.text()).build();
        protoGreetingService.addGreeting(greetingProto).subscribe().with(result -> {
            LOGGER.debug("result: {}", result);
        });
    }
}
