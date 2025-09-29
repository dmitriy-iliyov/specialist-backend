package com.specialist.profile.infrastructure.rest;

public interface LoginRestClient {
    void login(String clientId, String clientSecret) throws Exception;
}
