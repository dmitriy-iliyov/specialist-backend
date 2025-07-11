package com.aidcompass.users.doctor.specialization.models;

import com.aidcompass.users.general.exceptions.doctor.UnsupportedDoctorSpecializationTypeException;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DoctorSpecialization {
    ALLERGOLOGIST("Алерголог"),
    CARDIOLOGIST("Кардіолог"),
    DENTIST("Стоматолог"),
    ENDOCRINOLOGIST("Ендокринолог"),
    GASTROENTEROLOGIST("Гастроентеролог"),
    GENERAL_PRACTITIONER("Сімейний лікар"),
    GYNECOLOGIST("Гінеколог"),
    IMMUNOLOGIST("Імунолог"),
    INFECTIOUS_DISEASE_SPECIALIST("Інфекціоніст"),
    NEPHROLOGIST("Нефролог"),
    NEUROLOGIST("Невролог"),
    NEUROSURGEON("Нейрохірург"),
    NUTRITIONIST("Дієтолог"),
    OCCUPATIONAL_THERAPIST("Ерготерапевт"),
    ONCOLOGIST("Онколог"),
    OPHTHALMOLOGIST("Офтальмолог"),
    ORTHOPEDIST("Ортопед"),
    OTOLARYNGOLOGIST("Отоларинголог"),
    PEDIATRICIAN("Педіатр"),
    PSYCHIATRIST("Психіатр"),
    PSYCHOLOGIST("Психолог"),
    PSYCHOTHERAPIST("Психотерапевт"),
    PULMONOLOGIST("Пульмонолог"),
    REHABILITATION_SPECIALIST("Реабілітолог"),
    RHEUMATOLOGIST("Ревматолог"),
    SPEECH_THERAPIST("Логопед"),
    SPORTS_MEDICINE_SPECIALIST("Спеціаліст зі спортивної медицини"),
    SURGEON("Хірург");

    private final String translate;


    DoctorSpecialization(String translate) {
        this.translate = translate;
    }

    @JsonValue
    public String getTranslate() {
        return translate;
    }

    public static DoctorSpecialization toEnum(String translate) {
        for (DoctorSpecialization specialization : DoctorSpecialization.values()) {
            if (specialization.getTranslate().equalsIgnoreCase(translate)) {
                return specialization;
            }
        }
        throw new UnsupportedDoctorSpecializationTypeException();
    }

    @Override
    public String toString() {
        return translate;
    }
}