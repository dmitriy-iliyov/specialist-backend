package com.aidcompass.user.models.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberCreateDto extends BaseMemberDto {

        @JsonCreator
        public MemberCreateDto(@JsonProperty("last_name") String lastName,
                               @JsonProperty("first_name") String firstName,
                               @JsonProperty("second_name") String secondName) {
                super(lastName, firstName, secondName);
        }
}
