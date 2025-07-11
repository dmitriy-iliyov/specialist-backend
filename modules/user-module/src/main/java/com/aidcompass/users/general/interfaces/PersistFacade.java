package com.aidcompass.users.general.interfaces;

import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface PersistFacade<T, D> {
    D save(UUID userId, T dto, HttpServletResponse response);
}
