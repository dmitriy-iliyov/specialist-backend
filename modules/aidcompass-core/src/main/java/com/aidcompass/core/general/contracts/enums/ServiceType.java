package com.aidcompass.core.general.contracts.enums;

public enum ServiceType {
    DOCTOR_SERVICE("doctors"),
    JURIST_SERVICE("jurists"),
    CUSTOMER_SERVICE("customers");

    private final String cacheName;

    ServiceType(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public String toString() {
        return this.cacheName;
    }

    public String getCacheName() {
        return this.cacheName;
    }
}
