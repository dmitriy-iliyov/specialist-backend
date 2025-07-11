package com.aidcompass.users.jurist.specialization.models;

import com.aidcompass.users.general.exceptions.jurist.UnsupportedJuristTypeException;
import com.fasterxml.jackson.annotation.JsonValue;

public enum JuristType {
    NOTARY("Нотаріус"),
    LAWYER("Адвокат"),
    LEGAL_AID("Юрист системи БПД");

    private final String translate;

    JuristType(String translate) {
        this.translate = translate;
    }

    @JsonValue
    public String getTranslate() {
        return translate;
    }

    public static JuristType toEnum(String translate) {
        for (JuristType type : JuristType.values()) {
            if (type.getTranslate().equalsIgnoreCase(translate)) {
                return type;
            }
        }
        throw new UnsupportedJuristTypeException();
    }

    @Override
    public String toString() {
        return translate;
    }
}

