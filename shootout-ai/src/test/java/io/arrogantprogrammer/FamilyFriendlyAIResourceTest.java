package io.arrogantprogrammer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class FamilyFriendlyAIResourceTest {

    @Test
    void testHelloEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"text\": \"Hello, Mr. Spock\"}")
                .when()
                .get("/verify")
                .then()
                .statusCode(200);
    }

}