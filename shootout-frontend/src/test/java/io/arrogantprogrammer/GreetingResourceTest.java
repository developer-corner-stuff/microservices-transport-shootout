package io.arrogantprogrammer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    void testCreateGreeting() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new GreetingJSON("Hi, there!"))
                .post("/hello")
                .then()
                .statusCode(Response.Status.ACCEPTED.getStatusCode());
    }

}