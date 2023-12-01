package io.arrogantprogrammer;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class VerifyTest {

    @Inject
    Verifier verifier;

    @Test
    public void testVerificationLogic() {
        verifier.verifyText("Hello, Mr. Spock")
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create()).assertItem(true);
        verifier.verifyText("Hello, Capt. Kirk")
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create()).assertItem(true);
        verifier.verifyText("Hello, Bones")
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create()).assertItem(true);
        verifier.verifyText("Hello, VMWare")
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create()).assertItem(false);
        verifier.verifyText("Hello, Ubuntu")
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create()).assertItem(false);
    }

}
