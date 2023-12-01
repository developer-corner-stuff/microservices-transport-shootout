package io.arrogantprogrammer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class ListGreetingsTest {

    @BeforeEach
    @Transactional
    public void setup() {
        Greeting.deleteAll();
        Greeting greeting = new Greeting("Hello");
        greeting.persist();
    }

    @Test
    public void testListGreetingsEndpoint() {
        given().
                when()
                .header(new Header("Accept", "application/json"))
                .get("/greetings")
                .then()
                .statusCode(200)
                .body(is(notNullValue()));
    }
}
