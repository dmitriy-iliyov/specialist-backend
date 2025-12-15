package com.specialist.contracts.auth;

import java.util.List;

public interface DeferAccountDeleteHandler {
    void handle(List<DeferAccountDeleteEvent> events);
}
