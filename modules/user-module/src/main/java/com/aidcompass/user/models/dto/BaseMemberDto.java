package com.aidcompass.user.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public abstract class BaseMemberDto {
        @JsonIgnore
        protected UUID id;

        @JsonProperty("last_name")
        @NotBlank(message = "Last name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        protected final String lastName;

        @JsonProperty("first_name")
        @NotBlank(message = "First name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        protected final String firstName;

        @JsonProperty("second_name")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        protected final String secondName;

        protected MultipartFile avatar;

        protected String avatarUrl;
}