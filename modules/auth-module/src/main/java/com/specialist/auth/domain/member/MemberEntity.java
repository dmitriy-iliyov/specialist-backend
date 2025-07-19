package com.specialist.auth.domain.member;

import java.time.Instant;
import java.util.UUID;

public class MemberEntity {

    private UUID id;

    private String email;

    private String password;

    private Instant createdAt;

    private Instant updatedAt;


    public MemberEntity() {
        //this.id = UuidFa
    }
}
