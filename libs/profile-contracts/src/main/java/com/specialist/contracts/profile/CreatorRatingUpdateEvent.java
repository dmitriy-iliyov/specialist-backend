package com.specialist.contracts.profile;

import java.util.UUID;

public record CreatorRatingUpdateEvent(
        UUID id,
        UUID creatorId,
        long reviewCount,
        long earnedRating
) { }