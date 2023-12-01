package io.arrogantprogrammer;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class VerifyTest {

    @Inject
    Verifier verifier;

    @Test
    public void testVerificationLogic() {
        assertTrue(verifier.verifyText("Hello, Mr. Spock"));
        assertTrue(verifier.verifyText("Hello, Capt. Kirk"));
        assertTrue(verifier.verifyText("Hello, Bones"));
        assertFalse(verifier.verifyText("Hello, VMWare"));
    }

}
