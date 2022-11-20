package com.orehbein.graapi.domain.exception;

public class ProducerNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public ProducerNotFoundException(String message) {
        super(message);
    }

    public ProducerNotFoundException(Long id) {
        this(String.format("Unabled to find producer with code %d", id));
    }

}