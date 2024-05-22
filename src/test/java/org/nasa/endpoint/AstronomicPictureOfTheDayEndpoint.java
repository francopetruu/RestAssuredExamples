package org.nasa.endpoint;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.nasa.api.RequestBuilder;

public class AstronomicPictureOfTheDayEndpoint {

    public Response getPictureOfTheDay(String BASE_URL, String API_KEY) {
        RequestBuilder builder = new RequestBuilder()
                .withBaseUri(BASE_URL)
                .withApiKey(API_KEY);

        return builder.build().get();
    }

    public Response getPictureOfSpecificDate(String BASE_URL, String API_KEY, String date) {
        RequestBuilder builder = new RequestBuilder()
                .withBaseUri(BASE_URL)
                .withApiKey(API_KEY);

        return builder.build().get("?date={date}", date);
    }

    public ResponseBody getResponseBodyPictureOfTheDay(String BASE_URL, String API_KEY) {
        RequestBuilder builder = new RequestBuilder()
                .withBaseUri(BASE_URL)
                .withApiKey(API_KEY);

        return builder.build().get().getBody();
    }
}
