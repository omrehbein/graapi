package com.orehbein.graapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    INVALID_DATA("/invalid-data", "Invalid Data"),
    SYSTEM_ERROR("/system-error", "System Error"),
    INVALID_PARAM("/invalid-param", "Invalid Param"),
    INCOMPREENSIBLE_MESSAGE("/incompreensible-message", "Mensage incompreensible"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business rule violation");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://localhost" + path;//TODO
        this.title = title;
    }

}