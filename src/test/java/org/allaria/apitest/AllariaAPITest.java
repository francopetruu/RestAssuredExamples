package org.allaria.apitest;

import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.assertj.core.api.SoftAssertions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.assertAll;

public class AllariaAPITest {

    private String baseUrl = "https://api.allariamas.dev";
    private String bearerToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE3MDI0ODE3MjYsImV4cCI6MTczNDAxNzcxNiwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.yUc1LiXm_EfIGxOAPn683JbEazy1M_0mLkTqiCQjGcw";
    private String accountsEndpoint = "/challenge/qa/accounts";

    private SoftAssertions softAssertions = new SoftAssertions();

    @Test
    public void accountsEndpointStatusCodeIs200() {
        Response response = given()
                .headers("Authorization", "Bearer " + bearerToken)
                .when()
                .get(baseUrl + accountsEndpoint);

        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void idShouldNotBeNegativeNumber() {
        Response response = given()
                .headers("Authorization", "Bearer " + bearerToken)
                .when()
                .get(baseUrl + accountsEndpoint);

        JSONArray jsonArray = response.getBody().as(JSONArray.class);

        for (int i = 0; i<jsonArray.size(); i++) {
            LinkedHashMap<Object, Object> jsonObject = (LinkedHashMap<Object, Object>) jsonArray.get(i);

            int id = (int) jsonObject.get("id");

            assertAll("Should not be negative",
                    () -> assertTrue(id > 0)
            );
        }
    }

    @Test
    public void accountsListShouldNotBeEmpty() {
        Response response = given()
                .headers("Authorization", "Bearer " + bearerToken)
                .when()
                .get(baseUrl + accountsEndpoint);

        JSONArray jsonArray = response.getBody().as(JSONArray.class);

        assertNotNull(jsonArray);
    }

    @Test
    public void ownerListShouldNotBeEmpty() {
        Response response = given()
                .headers("Authorization", "Bearer " + bearerToken)
                .when()
                .get(baseUrl + accountsEndpoint);

        JSONArray jsonArray = response.getBody().as(JSONArray.class);

        for (int i = 0; i<jsonArray.size(); i++) {
            LinkedHashMap<Object, Object> jsonObject = (LinkedHashMap<Object, Object>) jsonArray.get(i);

            ArrayList<Object> owner = (ArrayList<Object>) jsonObject.get("owner");

            assertTrue(owner.size() > 0);
        }
    }

    @Test
    public void ownersAgeShouldBeGreaterThan18() {

        Response response = given()
                .headers("Authorization", "Bearer " + bearerToken)
                .when()
                .get(baseUrl + accountsEndpoint);

        JSONArray jsonArray = response.getBody().as(JSONArray.class);

        for (int i = 0; i<jsonArray.size(); i++) {
            LinkedHashMap<Object, Object> jsonObject = (LinkedHashMap<Object, Object>) jsonArray.get(i);

            ArrayList<Object> owner = (ArrayList<Object>) jsonObject.get("owner");

            for (int j = 0; j<owner.size(); j++) {
                LinkedHashMap<Object, Object> ownerObject = (LinkedHashMap<Object, Object>) owner.get(j);

                int age = (int) ownerObject.get("age");

                assertTrue( age >= 18);
            }
        }

    }

}
