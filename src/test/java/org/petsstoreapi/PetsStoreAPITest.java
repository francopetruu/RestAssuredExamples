package org.petsstoreapi;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetsStoreAPITest {

    private String baseUrl = "https://petstore3.swagger.io/api/v3";
    private String petEndpoint = "/pet";
    private String storeEndpoint = "/store";

    @Test
    @Order(1)
    public void testCreatePet() {
        String requestBody = "{ \"id\": 0, \"name\": \"Fido\", \"status\": \"available\" }";

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post("https://petstore3.swagger.io/api/v3/pet")
                        .then()
                        .statusCode(200)
                        .body("name", equalTo("Fido"))
                        .body("status", equalTo("available"))
                        .extract()
                        .response();

        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    @Order(2)
    public void testGetSpecificPet() {
        Response response =
                given()
                        .get("https://petstore3.swagger.io/api/v3/pet/0")
                        .then()
                        .statusCode(200)
                        .body("name", equalTo("Fido"))
                        .extract()
                        .response();
    }

    @Test
    @Order(3)
    public void testGetSpecificPetByStatus() {
        Response response =
                given()
                        .param("status", "available")
                        .get("https://petstore3.swagger.io/api/v3/pet/findByStatus")
                        .then()
                        .statusCode(200)
                        .body(notNullValue())
                        .extract()
                        .response();
        ;
    }

    @Test
    @Order(4)
    public void testUpdatePet() {
        String requestBody = "{ \"id\": 0, \"name\": \"Fido\", \"status\": \"NOTAVAILABLE\" }";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("https://petstore3.swagger.io/api/v3/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Fido"))
                .body("status", equalTo("NOTAVAILABLE"))
                .extract()
                .response();
    }

    @Test
    @Order(5)
    public void testDeletePet() {
        Response response =
                given()
                        .delete("https://petstore3.swagger.io/api/v3/pet/0")
                        .then()
                        .statusCode(200)
                        .body(equalTo("Pet deleted"))
                        .extract()
                        .response();
    }

    @Test
    @Order(6)
    public void testRetrieveNonExistingPet() {
        Response response =
                given()
                        .get("https://petstore3.swagger.io/api/v3/pet/0")
                        .then()
                        .statusCode(404)
                        .body(equalTo("Pet not found"))
                        .extract()
                        .response();
    }

    @Test
    @Order(7)
    public void testDeleteNonExistingPet() {
        Response response =
                given()
                        .get("https://petstore3.swagger.io/api/v3/pet/0")
                        .then()
                        .statusCode(404)
                        .body(equalTo("Pet not found"))
                        .extract()
                        .response();
    }

    @Test
    @Order(8)
    public void testMissingIdOnGetRequest() {
        Response response =
                given()
                        .get("https://petstore3.swagger.io/api/v3/pet")
                        .then()
                        .statusCode(405)
                        .body("message", equalTo("HTTP 405 Method Not Allowed"))
                        .extract()
                        .response();
    }

    @Test
    @Order(9)
    public void testMissingBodyOnPostRequest() {
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .post("https://petstore3.swagger.io/api/v3/pet")
                        .then()
                        .statusCode(400)
                        .body("message", containsString("Input error"))
                        .extract()
                        .response();
    }
}
