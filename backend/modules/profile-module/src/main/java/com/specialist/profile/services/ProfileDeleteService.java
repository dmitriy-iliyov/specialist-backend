package com.specialist.profile.services;

import com.specialist.contracts.auth.AccountDeleteEvent;

import java.util.List;

public interface ProfileDeleteService {
    void delete(List<AccountDeleteEvent> events);
}
