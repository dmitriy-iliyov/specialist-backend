package com.specialist.specialistdirectory.domain.bookmark.models;

import java.util.UUID;

public record BookmarkIdPair(
    UUID id,
    UUID specialistId
) { }
