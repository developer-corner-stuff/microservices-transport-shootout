package io.arrogantprogrammer;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class AllGreetingsTest {

    @InjectMock
    @RestClient
    CQRSClient cqrsClient;

    @BeforeEach
    @Transactional
    public void setup() {

        List<GreetingJSON> list = new ArrayList<GreetingJSON>(){{
            add(new GreetingJSON("Hello, World!"));
            add(new GreetingJSON("Hi, there!"));
        }};
        Mockito.when(cqrsClient.allGreetings()).thenReturn(list);
    }

    @Test
    void testAllGreetings() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .get("/hello/all")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", is(2));
    }
}
