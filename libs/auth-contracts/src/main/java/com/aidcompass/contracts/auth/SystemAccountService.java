package com.aidcompass.contracts.auth;

import java.util.UUID;

public interface SystemAccountService {
    //should return null when not found
    UUID findIdByEmail(String email);

    void updateEmailById(UUID id);

    void deleteById(UUID id);
}
