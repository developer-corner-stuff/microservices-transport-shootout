package io.arrogantprogrammer;

import io.arrogantprogrammer.proto.*;
import io.quarkus.arc.All;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.grpc.GrpcService;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.vertx.VertxContextSupport;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;

@GrpcService
public class GreetingResource implements ProtoGreetingService {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);

    @GrpcClient("ai")
    ProtoVerificationService verificationService;

    @Inject
    GreetingRepository greetingRepository;

    @Override @WithTransaction
    public Uni<AllGreetingProtos> getAllGreetings(Empty request) {

        return allGreetings().onItem().transform(greetingProtos -> {
            LOGGER.debug("getAllGreetings returning {} greetings", greetingProtos.size());
            return AllGreetingProtos.newBuilder().addAllGreetings(greetingProtos).build();
        });
    }

    @Blocking
    private Uni<List<GreetingProto>> allGreetings() {

        return greetingRepository.listAll().onItem().transform(response -> {
            return response.stream().map(greeting -> {
                LOGGER.debug("mapping greeting {} to GreetingProto", greeting.getText());
                return GreetingProto.newBuilder().setText(greeting.getText()).build();
            }).collect(toList());
        });
    }

    @Override @WithTransaction
    public Uni<GreetingProto> addGreeting(GreetingProto request) {

        return verificationService.verifyGreeting(UnverifiedGreetingProto.newBuilder().setText(request.getText()).build())
                .onItem()
                .transform(verifiedGreeting -> {
                        Greeting greeting = new Greeting(request.getText());
                        greeting.persistAndFlush();
                        LOGGER.debug("persisted greeting: {}", greeting);
                        return greeting;
                }).onItem().transform(greeting -> {
                    LOGGER.debug("adding greeting: {}", greeting);
                    return GreetingProto.newBuilder().setText(request.getText()).build();
                });

//        return verificationService.verifyGreeting(UnverifiedGreetingProto.newBuilder().setText(request.getText()).build())
//                .map(VerifiedGreetingProto::getIsFamilyFriendly)
//                .map(verifiedGreeting -> {
//                    if(verifiedGreeting.booleanValue()){
//                        LOGGER.debug("verified greeting: {}", request);
//                        Greeting greeting = new Greeting(request.getText());
//                        greeting.persistAndFlush();
//                        LOGGER.debug("persisted greeting: {}", request);
//                    }
//                    return GreetingProto.newBuilder().setText(request.getText()).build();
//                });
    }

}
