package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class AccessTokenSerializerImpl implements AccessTokenSerializer {

    @Value("${api.access-token.secret}")
    public String SECRET;

    @Override
    public String serialize(AccessToken accessToken) {

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        Header header = Jwts.header()
                .add("alg", key.getAlgorithm())
                .add("typ", "JWT")
                .build();

        Claims claims = Jwts.claims()
                .id(accessToken.id().toString())
                .subject(accessToken.subjectId().toString())
                .issuedAt(Date.from(accessToken.createdAt()))
                .expiration(Date.from(accessToken.expiresAt()))
                .add("authorities", accessToken.authorities())
                .build();

        return Jwts.builder()
                .header().add(header)
                .and()
                .claims(claims)
                .signWith(key)
                .compact();
    }
}
