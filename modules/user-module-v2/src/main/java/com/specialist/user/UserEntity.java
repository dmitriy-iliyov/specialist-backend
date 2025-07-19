package com.specialist.user;

import java.time.Instant;
import java.util.UUID;

public class UserEntity {

    private UUID id;

    private String fullName;

    private String email;

    private String avatarUrl;

    private double creatorRating;

    private Instant createdAt;

    private Instant updatedAt;
}
