package org.nasa.test;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import org.nasa.endpoint.AstronomicPictureOfTheDayEndpoint;
import utils.Config;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;

public class AstronomicPictureOfTheDayTest {

    private static String apiKey;
    private static AstronomicPictureOfTheDayEndpoint astronomicPictureOfTheDayEndpoint;
    private static String baseUrl;
    private static Config config;

    @Before
    public void setUp() {
        config = new Config();
        apiKey = config.getApiKey();
        baseUrl = config.getBaseUrl();
        astronomicPictureOfTheDayEndpoint = new AstronomicPictureOfTheDayEndpoint();
    }

    @Test
    public void getPictureOfTheDayTest() {
        Response response = astronomicPictureOfTheDayEndpoint.getPictureOfTheDay(baseUrl, apiKey);

        response.then()
                .statusCode(200)
                .body("size()", greaterThan(0));

        System.out.println(response.getBody().toString());
    }

    @Test
    public void getPictureOfTheDayOfSpecificDateTest() {
        Response response = astronomicPictureOfTheDayEndpoint.getPictureOfSpecificDate(baseUrl, apiKey, "2022-12-19");

        response.then()
                .statusCode(200)
                .body("size()", greaterThan(0));

        response.getBody().prettyPrint();
    }

    @Test
    public void verifyPictureOfTheDayResponseBody() {
        ResponseBody response = astronomicPictureOfTheDayEndpoint.getResponseBodyPictureOfTheDay(baseUrl, apiKey);

        String mediaType = "image" ;

        assertTrue(response.jsonPath().getString("media_type").equalsIgnoreCase(mediaType));
    }
}
