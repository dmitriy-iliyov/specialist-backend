package com.specialist.schedule.config;

public final class ScheduleCacheConfig {

    public static final String APPOINTMENT_DURATION_CACHE = "appointments:duration";
    public static final String APPOINTMENT_DURATION_MAP_CACHE = "appointments:duration:map";
    public static final String APPOINTMENTS_CACHE = "appointments";
    public static final String APPOINTMENTS_KEY_TEMPLATE = "appointments::%s";
    public final static String APPOINTMENTS_BY_DATE_AND_STATUS_CACHE = "appointments:date";
    public final static String APPOINTMENTS_BY_DATE_INTERVAL_CACHE = "appointments:date_interval";
    public final static String INTERVALS_BY_DATE_CACHE = "intervals:date";
    public final static String INTERVALS_BY_DATE_INTERVAL_CACHE = "intervals:date_interval";
    public final static String APPOINTMENT_CANCEL_TASK_EXISTS = "appointments:tasks:cancel:exists";

}