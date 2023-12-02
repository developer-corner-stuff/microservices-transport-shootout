package io.arrogantprogrammer;

import io.arrogantprogrammer.proto.ProtoVerificationService;
import io.arrogantprogrammer.proto.UnverifiedGreetingProto;
import io.arrogantprogrammer.proto.VerifiedGreetingProto;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AIServiceClient {

    static final Logger LOGGER = LoggerFactory.getLogger(AIServiceClient.class);

    @GrpcClient("ai")
    ProtoVerificationService verificationService;

    Uni<Boolean> isFamilyFriendly(GreetingJSON greetingJSON){
        LOGGER.debug("checking greeting: {}", greetingJSON);
        return verificationService.verifyGreeting(UnverifiedGreetingProto.newBuilder().setText(greetingJSON.text()).build())
                .map(VerifiedGreetingProto::getIsFamilyFriendly);
    };
}
