package com.specialist.contracts.auth;

import java.util.List;

public interface ImmediatelyAccountDeleteHandler {
    void handle(List<ImmediatelyAccountDeleteEvent> events);
}
