package org.testApi;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApiTest {

    private RequestSpecification requestSpecification;

    private String apikey = "zvEVEaHAIjlMFvpXeySDtRtA7jiASNja";
    private String baseUrl = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/";

    @Test
    public void checkResponseCodeIs200() {
        given()
                .queryParam("apikey", apikey)
        .when()
                .get(baseUrl + "7893")
        .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void checkBodyResponseDate() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toLowerCase();

        Response response = given()
                .queryParam("apikey", apikey)
                .when()
                .get(baseUrl + "7893");

        String body = response.getBody().asString().toLowerCase();

        assertTrue(body.contains(today));

    }
}
