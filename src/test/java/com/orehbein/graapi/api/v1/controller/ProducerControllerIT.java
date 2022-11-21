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
public class ProducerControllerIT {
    public static final String PREFIX = "/v1/producers";
    public static final String JSON_PATH = "/json/controller/ProducerControllerIT";
    @LocalServerPort
    private int port;

    @BeforeAll
    public static void beforeAll(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/v1/producers";
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
            .body("name", is("Aaron Seltzer"))
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
            .body("name", is("The Developer AI"))
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
            .body("name", is("The Developer AI2"))
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
    public void delete_validId_mustReturnCONFLICT_OK_Test() {
        given()
            .pathParam("id", 1)
            .port(port)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .delete("/{id}")
        .then()
            .statusCode(HttpStatus.CONFLICT.value())
        ;
    }

    @Test
    @Order(10)
    public void minMaxInterval_mustReturnExpectedBody_OK_Test() {
        given()
            .port(port)
            .accept(ContentType.JSON)
        .when()
            .get("/minmaxinterval")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("min.find {it.producer == 'Joel Silver'}.interval", is(1))
            .body("min[0].producer", is("Joel Silver"))
            .body("min[0].interval", is(1))
            .body("min[0].previousWin", is(1990))
            .body("min[0].followingWin", is(1991))

            .body("max[0].producer", is("Matthew Vaughn"))
            .body("max[0].interval", is(13))
            .body("max[0].previousWin", is(2002))
            .body("max[0].followingWin", is(2015))
        ;
    }
}
