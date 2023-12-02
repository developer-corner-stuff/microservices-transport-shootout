package io.arrogantprogrammer;

import io.arrogantprogrammer.proto.ProtoVerificationService;
import io.arrogantprogrammer.proto.UnverifiedGreetingProto;
import io.arrogantprogrammer.proto.VerifiedGreetingProto;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class FamilyFriendlyAIResource implements ProtoVerificationService {

    static final Logger LOGGER = LoggerFactory.getLogger(FamilyFriendlyAIResource.class);

    @Inject
    Verifier verifier;

    @Override
    public Uni<VerifiedGreetingProto> verifyGreeting(UnverifiedGreetingProto request) {
        boolean isFamilyFriendly = verifier.verifyText(request.getText());
        return Uni.createFrom().item(VerifiedGreetingProto.newBuilder().setText(request.getText()).setIsFamilyFriendly(isFamilyFriendly).build());
    }
}
