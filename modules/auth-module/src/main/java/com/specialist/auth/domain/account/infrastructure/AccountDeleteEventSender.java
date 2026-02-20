package com.specialist.auth.domain.account.infrastructure;

import com.specialist.contracts.auth.DeferAccountDeleteEvent;
import com.specialist.contracts.auth.ImmediatelyAccountDeleteEvent;

import java.util.List;

public interface AccountDeleteEventSender {
    void sendImmediately(List<ImmediatelyAccountDeleteEvent> events);

    void sendDefer(List<DeferAccountDeleteEvent> list);
}
