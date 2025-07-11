package com.aidcompass.users.detail;

import com.aidcompass.users.detail.models.DetailEntity;

import java.util.UUID;

public interface PersistEmptyDetailService {
    DetailEntity saveEmpty(UUID userId);
}
