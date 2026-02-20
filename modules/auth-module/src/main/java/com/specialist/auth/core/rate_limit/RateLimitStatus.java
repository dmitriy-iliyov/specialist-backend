package com.specialist.auth.core.rate_limit;

public enum RateLimitStatus {
    ALLOWED, BLOCKED;

    public static RateLimitStatus fromBoolean(Boolean b) {
        if (b == null) {
            return null;
        } else if (b.equals(Boolean.TRUE)) {
            return RateLimitStatus.ALLOWED;
        } else {
            return RateLimitStatus.BLOCKED;
        }
    }
}
