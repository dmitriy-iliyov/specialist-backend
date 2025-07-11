package com.aidcompass.core.security.exceptions.not_found;

import com.aidcompass.core.general.exceptions.models.UserNotFoundException;

public class UserNotFoundByUsernameException extends UserNotFoundException {

    private final static String MESSAGE = "User not found";

    public UserNotFoundByUsernameException() {
        super(MESSAGE);
    }


}
