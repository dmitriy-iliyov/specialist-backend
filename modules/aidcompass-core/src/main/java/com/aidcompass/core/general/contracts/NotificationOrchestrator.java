package com.aidcompass.core.general.contracts;

import com.aidcompass.core.general.contracts.dto.BaseSystemVolunteerDto;
import com.aidcompass.core.general.contracts.dto.BatchRequest;

public interface NotificationOrchestrator {
    void greeting(BaseSystemVolunteerDto dto);

    Boolean remind(BatchRequest batchRequest);
}
