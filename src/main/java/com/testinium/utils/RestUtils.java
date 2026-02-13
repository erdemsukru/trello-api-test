package com.testinium.utils;

import com.testinium.config.TrelloConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestUtils {

    protected RequestSpecification given() {
        return RestAssured.given()
                .baseUri(TrelloConfig.BASE_URL)
                .contentType(ContentType.JSON)
                .queryParam(TrelloConfig.KEY_PARAM, TrelloConfig.API_KEY)
                .queryParam(TrelloConfig.TOKEN_PARAM, TrelloConfig.TOKEN);
    }

    protected Response post(String endpoint, Object body) {
        return given()
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response put(String endpoint, Object body) {
        return given()
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response get(String endpoint) {
        return given()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response delete(String endpoint) {
        return given()
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }

    protected void logResponse(Response response, String message) {
        System.out.println("\n=== " + message + " ===");
        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody().asPrettyString());
    }
}