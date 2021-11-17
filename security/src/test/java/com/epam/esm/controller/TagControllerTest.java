package com.epam.esm.controller;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagControllerTest {
    private static RequestSpecification keycloakServiceRequestSpec;
    private static String access_token;

    private String getAdminAccessToken() {
        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("client_id", "mjc_client")
                        .formParam("grant_type", "password")
                        .formParam("client_secret", "2351277d-79c6-46a7-9a8d-35327bd8c149")
                        .formParam("scope", "openid")
                        .formParam("username", "admin")
                        .formParam("password", "admin")
                        .when()
                        .post("http://localhost:8080/auth/realms/mjc/protocol/openid-connect/token");
        String json = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(json);
        return jsonPath.getString("access_token");
    }

    private String getUserAccessToken() {
        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("client_id", "mjc_client")
                        .formParam("grant_type", "password")
                        .formParam("client_secret", "2351277d-79c6-46a7-9a8d-35327bd8c149")
                        .formParam("scope", "openid")
                        .formParam("username", "user")
                        .formParam("password", "user")
                        .when()
                        .post("http://localhost:8080/auth/realms/mjc/protocol/openid-connect/token");
        String json = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(json);
        return jsonPath.getString("access_token");
    }

    @Test
    void shouldReturnAccessTokenTest() {
        Assertions.assertNotNull(getAdminAccessToken());
    }


    @Test
    void shouldReturn200CodeWhenFindOrderById() {
        String token = getAdminAccessToken();
        Response response =
                given()
                        .auth().oauth2(token)
                        .when().get("http://localhost:8001/v2/orders/1");
        Assertions.assertEquals(200, response.getStatusCode());
    }


    @Test
    void shouldReturn403CodeWhenFindByUserIdByAdmin() {
        String token = getAdminAccessToken();
        Response response =
                given()
                        .auth().oauth2(token)
                        .when().get("http://localhost:8001/v2/orders/users/10");
        Assertions.assertEquals(403, response.getStatusCode());
    }

    @Test
    void shouldReturn200CodeWhenFindByUserIdByAdmin() {
        String token = getAdminAccessToken();
        Response response =
                given()
                        .auth().oauth2(token)
                        .when().get("http://localhost:8001/v2/orders/users/2");
        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    void shouldRetutn() {
        String token = getUserAccessToken();
        Response response=
                given()
                        .auth().oauth2(token)
                        .when().get("http://localhost:8001/v2/users/1");
        Assertions.assertEquals(403,response.getStatusCode());
    }

}


