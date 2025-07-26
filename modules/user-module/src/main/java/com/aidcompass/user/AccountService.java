package com.aidcompass.user;

import java.util.UUID;

public interface AccountService {
    //should return null when not found
    UUID findIdByEmail(String email);

    void updateEmailById(UUID id);
}
