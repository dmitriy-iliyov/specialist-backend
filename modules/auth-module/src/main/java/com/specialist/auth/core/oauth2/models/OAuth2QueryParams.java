package com.specialist.auth.core.oauth2.models;

public record OAuth2QueryParams(
        Provider provider,
        String code,
        String state
) { }
