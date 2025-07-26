package com.aidcompass.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserKafkaListener {

    private final UserService userService;


    public void updateCreatorRating() {

    }
}
