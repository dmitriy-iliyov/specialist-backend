package com.specialist.auth.core.oauth2.models;

public record OAuth2QueryParams(
        String code,
        String state
) { }
