package io.arrogantprogrammer;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.stream.Stream;

@ApplicationScoped
public class Verifier {

    static boolean verifyText(String text) {
        try {
            Stream.of("VMWare", "Ubuntu").forEach(s -> {
                if (text.contains(s)) {
                    throw new RuntimeException("Unacceptable greeting");
                }
            });
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }
}
