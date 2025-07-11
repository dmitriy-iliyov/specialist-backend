package com.aidcompass.core.security.domain.token.serializing;


import com.aidcompass.core.security.domain.token.models.Token;

public interface TokenDeserializer {

    Token deserialize(String token);

}
