package com.aidcompass.specialistdirectory.domain.review.services;

import com.aidcompass.specialistdirectory.domain.review.models.NextOperation;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.aidcompass.specialistdirectory.domain.review.ReviewRepository;
import com.aidcompass.specialistdirectory.domain.review.services.interfases.ReviewService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public Pair<NextOperation, List<ReviewResponseDto>> update(ReviewUpdateDto dto) {
        return null;
    }

    @Transactional
    @Override
    public ReviewResponseDto deleteByCreatorIdAndId(UUID creatorId, UUID id) {
        return null;
    }
}
