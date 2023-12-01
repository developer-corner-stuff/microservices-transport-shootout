package io.arrogantprogrammer;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class GreetingServiceTest {

    @Inject
    GreetingService greetingService;

    @BeforeEach
    @Transactional
    public void setup() {

        Greeting.deleteAll();

        AIServiceClient client = new AIServiceClient() {
            @Override
            public boolean isFamilyFriendly(GreetingJSON greetingJSON) {
                if(greetingJSON.text().contains("VMWare"))
                    return false;
                else if(greetingJSON.text().contains("Ubuntu"))
                    return false;
                else
                    return true;
            }
        };
        QuarkusMock.installMockForType(client, AIServiceClient.class, RestClient.LITERAL);
    }
    @Test
    @Transactional
    public void testGreetingServiceVerifiesGreetingBeforePersisting() {
        greetingService.addGreeting(new GreetingJSON("Live long and prosper"));
        greetingService.addGreeting(new GreetingJSON("I Love VMWare"));
        assertEquals(1, Greeting.count());
    }
}
