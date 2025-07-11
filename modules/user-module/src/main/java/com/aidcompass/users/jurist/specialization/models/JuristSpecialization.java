package com.aidcompass.users.jurist.specialization.models;

import com.aidcompass.users.general.exceptions.jurist.UnsupportedJuristSpecializationException;
import com.fasterxml.jackson.annotation.JsonValue;

public enum JuristSpecialization {
    REFUGEE_RIGHTS("Спеціаліст з прав ВПО"),
    ADMINISTRATIVE_LAW("Адміністративне право"),
    SOCIAL_LAW("Соціальне забезпечення"),
    HOUSING_LAW("Житлове право"),
    PROPERTY_LAW("Майнові спори"),
    FAMILY_LAW("Сімейне право"),
    MIGRATION_LAW("Міграційне право");

    private final String translate;

    JuristSpecialization(String translate) {
        this.translate = translate;
    }

    @JsonValue
    public String getTranslate() {
        return translate;
    }

    public static JuristSpecialization toEnum(String translate) {
        for (JuristSpecialization specialization: JuristSpecialization.values()) {
            if (specialization.getTranslate().equalsIgnoreCase(translate)) {
                return specialization;
            }
        }
        throw new UnsupportedJuristSpecializationException();
    }

    @Override
    public String toString() {
        return translate;
    }
}
