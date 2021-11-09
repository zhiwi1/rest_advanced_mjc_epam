package com.epam.esm.controller;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import com.baeldung.roles.custom.persistence.model.Foo;
//
//import io.restassured.RestAssured;
//import io.restassured.authentication.FormAuthConfig;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//
//import org.junit.Test;

import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.authentication.OAuthSignature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TagControllerTest {
    private static RequestSpecification keycloakServiceRequestSpec;
    private static String access_token;
    private void setKeycloakServiceSpecs() {
//        keycloakServiceRequestSpec = new RequestSpecBuilder()
//                .setContentType(ContentType.URLENC)
//                .build();
    }


    @Test
    void getAccessToken() {

              Response response =
                given()
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("client_id","my_client")
                        .formParam("grant_type","password")
                        .formParam("client_secret","dd2dfa28-e7c2-4c1a-8a97-0d43556dadd1")
                        .formParam("scope","openid")
                        .formParam("username","admin")
                        .formParam("password","admin")
                        .when()
                        .post("http://localhost:8080/auth/realms/myrealm/protocol/openid-connect/token").
                        then().
                        assertThat().statusCode(200).extract().response();

        String json = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(json);
        access_token = jsonPath.getString("access_token");
        Assertions.assertNotNull(access_token);
    }

//    @Test
//    void givenUserWithReadPrivilegeAndHasPermission_whenGetFooById_thenOK() {
//        Response response = given().auth().oauth2("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOQzRYZkptTUVUdUNiU1h1TFdHa3J0SlE0R3ZJaDVnQXdybGwwMkR3V0V3In0.eyJleHAiOjE2MzYzOTA4MjksImlhdCI6MTYzNjM4MzYyOSwianRpIjoiZGNmNTExMzMtZDY3NC00NzMzLWI3MzUtZDdlODlhMjk5MDkyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL215cmVhbG0iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiYTBlMmQyZmItOWFlYy00NmU2LWE4MDQtMzZhODk5YTdlMWY0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoibXlfY2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6ImNlMTNiZTI1LThhNzYtNDBhMy04YTdmLWYwNTNmM2VkYzRkYyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1teXJlYWxtIiwib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibXlfY2xpZW50Ijp7InJvbGVzIjpbImFkbWluIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiY2UxM2JlMjUtOGE3Ni00MGEzLThhN2YtZjA1M2YzZWRjNGRjIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbiIsImN1c3RvbVBlcm1pc3Npb24iOiJHbG9iYWwifQ.V9yQK5AmJfhWFs7pNxfUBd81bxeyEwdbY-q8HbGQDoPVaGjqNIP4gMYFdx2-J-MyUKE80wewFywvltwezBCMLaFzDOVtzWrVOAMXQMokuaaUY6FJIjWydWp_7R803A8dr7GAO-YxUqN8IRLqyX2YSVxJsm3UjtQ1Bi2LqnakJLHKe3wqbKDldKxhMRkdLCAS-MyLUeNDh78bN0CIbFCT62-njOFC6gawk1Ao-AEbOokq6tQ1CNuMQT7fusUV5R1ZaP5X9FHzMTa7AIAfHrN9F4YqotxfTj0edhohKOS8rFjyJySmYqnbTIfJLBsr4hJGBZVyB3alx4tI9M0Akl5k3Q", OAuthSignature.HEADER);
//        assertEquals(200, response.getStatusCode());
//        assertTrue(response.asString().contains("id"));
//    }
}
// @Test
//    public void givenUserWithNoWritePrivilegeAndHasPermission_whenPostFoo_thenForbidden() {
//        Response response = givenAuth("john", "123").contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(new Foo("sample"))
//                .post("http://localhost:8082/foos");
//        assertEquals(403, response.getStatusCode());
//    }
//
//    @Test
//    public void givenUserWithWritePrivilegeAndHasPermission_whenPostFoo_thenOk() {
//        Response response = givenAuth("tom", "111").contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(new Foo("sample"))
//                .post("http://localhost:8082/foos");
//        assertEquals(201, response.getStatusCode());
//        assertTrue(response.asString().contains("id"));
//    }
//}
