package com.aidcompass.specialistdirectory.domain.specialist.models.dtos;

public record RatingDto(
        double totalRating,
        long totalReview
) { }