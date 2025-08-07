package com.specialist.auth.infrastructure.message.configs;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageConfig {

    public static final String ACCOUNT_CONFIRMATION = "Ваш код підтвердження: %s. " +
            "\nВведіть цей код для підтвердження вашого акаунту. Якщо ви не запитували код, ігноруйте це повідомлення.";

    public static final String PASS_RECOVERY = "Ваш код підтвердження: %s. " +
            "\nВведіть цей код для відновлення паролю від вашого акаунту разом з новим паролем. " +
            "Якщо ви не запитували код, ігноруйте це повідомлення.";
}
