package com.specialist.auth.infrastructure.message.configs;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageConfig {

    public static final String ACCOUNT_CONFIRMATION = "Your verification code is: %s." +
            "\nEnter this code to verify your account. If you did not request this code, please ignore this message.";

    public static final String PASS_RECOVERY = "Your verification code is: %s." +
            "\nEnter this code to reset your account password together with your new password." +
            "\nIf you did not request this code, please ignore this message.";
}
