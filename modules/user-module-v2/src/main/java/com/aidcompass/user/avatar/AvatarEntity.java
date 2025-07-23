package com.aidcompass.user.avatar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "avatars")
public class AvatarEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String url;

    @Column(name = "file_name", nullable = false)
    private String fileName;
}
