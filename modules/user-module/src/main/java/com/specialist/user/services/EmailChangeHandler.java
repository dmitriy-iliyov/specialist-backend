package com.specialist.user.services;

import java.util.UUID;

@FunctionalInterface
public interface EmailChangeHandler {
    void handle(UUID id, String email);
}
