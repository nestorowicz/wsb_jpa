package com.jpacourse.rest.exception.impl;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(Long id) {
        super(String.format("Could not find address of ID: %s", id));
    }
}