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

//        List<GreetingProto> allGreetingProtos =
//                greetingRepository.listAll().await().indefinitely()
//                        .stream()
//                        .map(greeting -> GreetingProto.newBuilder().setText(greeting.getText()).build()).collect(toList());


        return allGreetings().onItem().transform(greetingProtos -> {
            LOGGER.debug("getAllGreetings returning {} greetings", greetingProtos.size());
            return AllGreetingProtos.newBuilder().addAllGreetings(greetingProtos).build();
        });
//        return Uni.createFrom().item(allGreetings()).onItem().transform(greetings -> {
//            return GreetingProto.newBuilder().setText(greetings);
//        }).map(greetings -> AllGreetingProtos.newBuilder()
//                .addAllGreetings(greetings.stream()
//                        .map(greeting -> GreetingProto.newBuilder().setText(greeting.getText()).build()).collect(toList())).build());
//        try {
//                return Uni.createFrom().item(AllGreetingProtos.newBuilder()
//                        .addAllGreetings(greetingRepository.listAll().await().indefinitely()
//                                .stream()
//                                .map(greeting -> GreetingProto.newBuilder().setText(greeting.getText()).build()).collect(toList())).build());
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
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


//    public Uni<List<GreetingProto>> listGreetings() {
//        LOGGER.debug("listGreetings");
//        Greeting.listAll();
////        List<Greeting> allGreetings = Greeting.listAll();
////        LOGGER.debug("listGreetings found {} greetings", allGreetings.size());
////        List<GreetingJSON> greetings = allGreetings.stream().map(greeting -> new GreetingJSON(greeting.text)).toList();
////        LOGGER.debug("listGreetings returning {} greetings", greetings.size());
////        return Greeting.listAll().subscribe().with(result -> {
////            result.stream().map(greeting -> new GreetingJSON((Greeting) greeting.getText())).toList();
////        }).collect().asList();
//        return (Uni<List<GreetingJSON>>) Greeting.listAll().subscribe().with(result -> {
//            result.stream().map(Greeting.class::cast)
//                    .map(greeting -> new GreetingJSON(greeting.getText())).toList();
//        });
//    }

    @Override @WithTransaction
    public Uni<GreetingProto> addGreeting(GreetingProto request) {

        return verificationService.verifyGreeting(UnverifiedGreetingProto.newBuilder().setText(request.getText()).build())
                .map(VerifiedGreetingProto::getIsFamilyFriendly)
                .map(verifiedGreeting -> {
                    if(verifiedGreeting.booleanValue()){
                        LOGGER.debug("verified greeting: {}", request);
                        Greeting greeting = new Greeting(request.getText());
                        greeting.persist();
                        LOGGER.debug("persisted greeting: {}", request);
                    }
                    return GreetingProto.newBuilder().setText(request.getText()).build();
                });
    }

}
