package com.specialist.auth.core.oauth2;

public record OAuth2QueryParams(
        String code,
        String state,
        String redirectUrl
) { }
