package com.orehbein.graapi.domain.exception;

public class StudioNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public StudioNotFoundException(String message) {
        super(message);
    }

    public StudioNotFoundException(Long id) {
        this(String.format("Unabled to find studio with code %d", id));
    }

}