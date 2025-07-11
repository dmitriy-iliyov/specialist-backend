package com.aidcompass.core.security.exceptions.not_found;

import com.aidcompass.core.general.exceptions.models.UserNotFoundException;

public class UserNotFoundByNumberException extends UserNotFoundException {

    private final static String MESSAGE = "User not found";

    public UserNotFoundByNumberException() {
        super(MESSAGE);
    }
}