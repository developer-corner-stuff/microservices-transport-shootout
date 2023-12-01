package io.arrogantprogrammer;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.stream.Stream;

@ApplicationScoped
public class Verifier {

    static Uni<Boolean> verifyText(String text) {
        return Uni.createFrom().item(text)
                .onItem().transform(t -> {
                    try {
                        Stream.of("VMWare", "Ubuntu").forEach(s -> {
                            if (t.contains(s)) {
                                throw new RuntimeException("Unacceptable greeting");
                            }
                        });
                    } catch (RuntimeException e) {
                        return false;
                    }
                    return true;
                });
    }

}
