package com.aidcompass.specialistdirectory.domain.review.services.interfases;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ReviewUserAggregator {
    Map<UUID, String> findAvatarsByIdIn(Set<UUID> userIds);
    Map<UUID, String> findUsernamesByIdIn(Set<UUID> userIds);
}
