package com.aidcompass.contracts.user;

import java.util.UUID;

public record CreatorRatingUpdateEvent(
        UUID id,
        UUID creatorId,
        long reviewCount,
        long earnedRating
) {
}
