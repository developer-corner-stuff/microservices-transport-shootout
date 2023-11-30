package io.arrogantprogrammer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {

    @Test
    void testAddGreetingEndpoint() {
        given()
          .when()
                .header(new Header("Content-Type", ContentType.JSON.toString()))
                .post("/greetings")
          .then()
             .statusCode(200)
             .body(is("Hello from RESTEasy Reactive"));
    }

}