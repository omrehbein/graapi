package com.orehbein.graapi.domain.exception;

public class MovieNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public MovieNotFoundException(String message) {
        super(message);
    }

    public MovieNotFoundException(Long id) {
        this(String.format("Unabled to find movie with code %d", id));
    }

}