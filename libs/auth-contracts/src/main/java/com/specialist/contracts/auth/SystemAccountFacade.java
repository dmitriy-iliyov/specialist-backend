package com.specialist.contracts.auth;

import java.util.UUID;

public interface SystemAccountFacade {
    //should return null when not found
    UUID findIdByEmail(String email);

    void updateEmailById(UUID id, String email);

    void deleteById(UUID id);
}
