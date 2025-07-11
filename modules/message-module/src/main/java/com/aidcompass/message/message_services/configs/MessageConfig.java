package com.aidcompass.message.message_services.configs;

public class MessageConfig {

    public static final String ACCOUNT_CONFIRMATION = "Ваш код підтвердження: %s. " +
            "\nВведіть цей код для підтвердження вашого акаунту. Якщо ви не запитували код, ігноруйте це повідомлення.";

    public static final String PASS_RECOVERY = "Ваш код підтвердження: %s. " +
            "\nВведіть цей код для відновлення паролю від вашого акаунту разом з новим паролем. " +
            "Якщо ви не запитували код, ігноруйте це повідомлення.";

    public static final String RESOURCE_CONFIRMATION = "Ваш код підтвердження: %s. " +
            "\nВведіть цей код для підтвердження %s. Якщо ви не запитували код, ігноруйте це повідомлення.";

    public static final String GREETING = "%s %s, вітаємо у Aid Compass, Ваш профіль був успішно підтверджений, " +
            "дякуємо, що долучаетесь до нас, гарного дня!";

    public static final String CUSTOMER_APPOINTMENT_SCHEDULED_INFORMATION = "Вітаємо, %s %s, Ви створили запис на %s консультацію до %s %s %s на %s, " +
            "ми Вам нагадаємо за день до прийому!";

    public static final String VOLUNTEER_APPOINTMENT_SCHEDULED_INFORMATION = "Вітаємо, %s %s, користувач %s %s записався до Вас на %s консультацію! Консультація запланована на %s. Коментар до консультації залишений користувачем: \"%s\"";

    public static final String CUSTOMER_APPOINTMENT_SELF_CANCELED_INFORMATION = "Вітаємо, %s %s! Ви скасували свій запис на %s консультацію заплановану на %s. " +
            "Якщо Ви бажаєте записатися знову — скористайтеся нашим сервісом або зверніться до адміністратора.";

    public static final String VOLUNTEER_APPOINTMENT_CANCELED_INFORMATION = "Вітаємо, %s %s, користувач %s %s скасував запис на %s консультацію заплановану на %s!";

    public static final String VOLUNTEER_APPOINTMENT_SELF_CANCELED_INFORMATION = "Вітаємо, %s %s! Ви скасували запис користувача %s %s на %s консультацію заплановану на %s.";

    public static final String CUSTOMER_APPOINTMENT_CANCELED_INFORMATION =
            "Вітаємо, %s %s! Вимушені повідомити, що запис на %s консультацію, заплановану на %s, було скасовано волонтером з поважних причин. " +
                    "Якщо бажаєте обрати інший час — скористайтеся нашим сервісом.";

    public static final String CUSTOMER_APPOINTMENT_REMINDER_INFORMATION = "Вітаємо, %s %s! %s ви записані на %s консультацію.";
}
