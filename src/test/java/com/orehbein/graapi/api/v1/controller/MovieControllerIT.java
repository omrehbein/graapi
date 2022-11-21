package com.orehbein.graapi.api.v1.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.test.context.TestPropertySource;
import testutil.ResourceUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class MovieControllerIT {
    public static final String PREFIX = "/v1/movies";
    public static final String JSON_PATH = "/json/controller/MovieControllerIT";
    @LocalServerPort
    private int port;

    @BeforeAll
    public static void beforeAll(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/v1/movies";
    }

    @Test
    @Order(1)
    public void get_mustReturn_OK_Test() {
        given()
            .basePath(PREFIX)
            .port(port)
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
        ;
    }

    @Test
    @Order(2)
    public void get_validId_mustReturnExpectedBody_OK_Test() {
        given()
            .pathParam("id", 2)
            .port(port)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("title", is("Cruising"))
        ;
    }
	
    @Test
    @Order(3)
    public void get_invalidId_mustReturnNOT_FOUND_FAIL_Test() {
        given()
            .pathParam("id", 9999)
            .port(port)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
        ;
    }

    @Test
    @Order(4)
    public void put_validId_mustReturnExpectedBody_OK_Test() {
        final var input = ResourceUtils.getContentFromResource(JSON_PATH + "/put_validId_mustReturnExpectedBody_OK_Test/input.json");
        given()
            .pathParam("id", 1)
            .body(input)
            .port(port)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .put("/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("title", is("Can't Stop the Music XX"))
        ;
    }

    @Test
    @Order(5)
    public void put_invalidId_mustReturnNOT_FOUND_Fail_Test() {
        final var input = ResourceUtils.getContentFromResource(JSON_PATH + "/put_invalidId_mustReturnNOT_FOUND_Fail_Test/input.json");
        given()
            .pathParam("id", 99999)
            .body(input)
            .port(port)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .put("/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
        ;
    }

    @Test
    @Order(6)
    public void put_invalidBody_mustReturnBAD_REQUEST_Fail_Test() {
        final var input = ResourceUtils.getContentFromResource(JSON_PATH + "/put_invalidBody_mustReturnBAD_REQUEST_Fail_Test/input.json");
        given()
            .pathParam("id", 1)
            .body(input)
            .port(port)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .put("/{id}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
        ;
    }


    @Test
    @Order(7)
    public void post_mustReturnExpectedBody_OK_Test() {
        final var input = ResourceUtils.getContentFromResource(JSON_PATH + "/post_mustReturnExpectedBody_OK_Test/input.json");
        given()
            .body(input)
            .port(port)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("title", is("The Developer Movie"))
        ;
    }

    @Test
    @Order(8)
    public void post_invalidBody_mustReturnBAD_REQUEST_Fail_Test() {
        final var input = ResourceUtils.getContentFromResource(JSON_PATH + "/post_invalidBody_mustReturnBAD_REQUEST_Fail_Test/input.json");
        given()
            .body(input)
            .port(port)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
        ;
    }
	
    @Test
    @Order(9)
    public void delete_validId_mustReturnOK_OK_Test() {
        given()
            .pathParam("id", 1)
            .port(port)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .delete("/{id}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value())
        ;
    }

}
