package com.aidcompass.core.security.configs;

import com.aidcompass.core.security.domain.token.factory.TokenFactory;
import com.aidcompass.core.security.domain.token.factory.TokenFactoryImpl;
import com.aidcompass.core.security.domain.token.serializing.TokenDeserializer;
import com.aidcompass.core.security.domain.token.serializing.TokenDeserializerImpl;
import com.aidcompass.core.security.domain.token.serializing.TokenSerializer;
import com.aidcompass.core.security.domain.token.serializing.TokenSerializerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TokenToolsConfig {

    @Value("${auth.token.secret}")
    private String SECRET;


    @Bean
    public TokenSerializer tokenSerializer(){
        return new TokenSerializerImpl(SECRET);
    }

    @Bean
    public TokenDeserializer tokenDeserializer() {
        return new TokenDeserializerImpl(SECRET);
    }

    @Bean
    public TokenFactory tokenFactory() {
        return new TokenFactoryImpl();
    }

}