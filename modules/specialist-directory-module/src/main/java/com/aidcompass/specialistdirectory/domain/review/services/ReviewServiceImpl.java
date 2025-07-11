package com.aidcompass.specialistdirectory.domain.review.services;

import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.aidcompass.specialistdirectory.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;


    @Transactional
    @Override
    public ReviewResponseDto save(ReviewCreateDto dto) {

        return null;
    }

    @Transactional
    @Override
    public ReviewResponseDto update(ReviewUpdateDto dto) {
        return null;
    }

    @Transactional
    @Override
    public void delete(UUID creatorId, UUID specialistId, UUID id) {

    }
}
