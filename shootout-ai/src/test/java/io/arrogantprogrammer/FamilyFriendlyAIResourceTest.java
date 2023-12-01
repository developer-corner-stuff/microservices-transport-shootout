package io.arrogantprogrammer;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.smallrye.reactive.messaging.memory.InMemorySink;
import io.smallrye.reactive.messaging.memory.InMemorySource;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class FamilyFriendlyAIResourceTest {

    static final Logger LOGGER = LoggerFactory.getLogger(FamilyFriendlyAIResourceTest.class);

    @Inject
    @Any
    InMemoryConnector connector;

    @BeforeAll
    public static void switchMyChannels() {
        InMemoryConnector.switchIncomingChannelsToInMemory("greetings-verifications");
        InMemoryConnector.switchOutgoingChannelsToInMemory("greetings-verified");
    }

    @AfterAll
    public static void revertMyChannels() {
        InMemoryConnector.clear();
    }

    @Test
    void testHelloEndpoint() {
        InMemorySource<UnverifiedGreetingJSON> incoming = connector.source("greetings-verifications");
        InMemorySink<VerifiedGreetingJSON> outgoing = connector.sink("greetings-verified");

        incoming.send(new UnverifiedGreetingJSON("Hello, Mr. Spock"));
        LOGGER.debug("sending valid greeting");
        incoming.send(new UnverifiedGreetingJSON("Hello, Capt. Kirk"));
        LOGGER.debug("sending valid greeting");
        incoming.send(new UnverifiedGreetingJSON("Hello, Bones"));
        LOGGER.debug("sending valid greeting");
        incoming.send(new UnverifiedGreetingJSON("Hello, VMWare"));
        LOGGER.debug("sending invalid greeting");
        incoming.send(new UnverifiedGreetingJSON("Hello, Ubuntu"));
        LOGGER.debug("sending invalid greeting");

        int recieved = outgoing.received().size();
        LOGGER.debug("recieved: {}", recieved);
        assertEquals(3, recieved);    }

}