package com.aidcompass.core.security.domain;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface BaseUserDetails extends UserDetails, CredentialsContainer {
    UUID getId();
}
