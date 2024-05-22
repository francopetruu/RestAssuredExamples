package org.nasa.api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class RequestBuilder {

    private RequestSpecification requestSpec;

    public RequestBuilder() {
        requestSpec = RestAssured.given();
    }

    public RequestBuilder withApiKey(String apiKey) {
        requestSpec.header("x-api-key", apiKey);
        return this;
    }

    public RequestBuilder withBaseUri(String baseUri) {
        requestSpec.baseUri(baseUri);
        return this;
    }

    public RequestBuilder withContentType(String contentType) {
        requestSpec.contentType(contentType);
        return this;
    }

    public RequestBuilder withBody(Object body) {
        requestSpec.body(body);
        return this;
    }

    public RequestSpecification build() {
        return requestSpec;
    }
}
