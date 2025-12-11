package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.mappers.ReviewMapper;
import com.specialist.specialistdirectory.domain.review.models.ReviewEntity;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.specialistdirectory.domain.review.models.enums.NextOperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewAgeType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewStatus;
import com.specialist.specialistdirectory.domain.review.models.filters.AdminReviewSort;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.specialistdirectory.domain.review.repositories.ReviewRepository;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.exceptions.NotAffiliatedToSpecialistException;
import com.specialist.specialistdirectory.exceptions.OwnershipException;
import com.specialist.specialistdirectory.exceptions.ReviewNotFoundByIdException;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;
    private final ReviewMapper mapper;


    @Transactional
    @Override
    public ReviewResponseDto save(SpecialistEntity specialist, ReviewCreateDto dto) {
        ReviewEntity entity = mapper.toEntity(dto);
        entity.setSpecialist(specialist);
        entity.setStatus(ReviewStatus.UNAPPROVED);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    @Override
    public void approve(UUID specialistId, UUID id, ApproverType approver) {
        ReviewEntity entity = repository.findById(id).orElseThrow(ReviewNotFoundByIdException::new );
        this.assertSpecialistAffiliation(entity.getSpecialist().getId(), specialistId);
        entity.setStatus(ReviewStatus.APPROVED);
        entity.setApprover(approver);
        repository.save(entity);
    }

    @Transactional
    @Override
    public Pair<NextOperationType, Map<ReviewAgeType, ReviewResponseDto>> update(ReviewUpdateDto newReview) {
        Pair<NextOperationType, Map<ReviewAgeType, ReviewResponseDto>> resultPair;
        Map<ReviewAgeType, ReviewResponseDto> resultMap = new HashMap<>();

        ReviewEntity existedReview = repository.findById(newReview.getId()).orElseThrow(ReviewNotFoundByIdException::new);
        this.assertOwnership(existedReview.getCreatorId(), newReview.getCreatorId());
        this.assertSpecialistAffiliation(existedReview.getSpecialist().getId(), newReview.getSpecialistId());

        resultMap.put(ReviewAgeType.OLD, mapper.toDto(existedReview));
        if (existedReview.getRating() != newReview.getRating()) {
            existedReview.setRating(newReview.getRating());
            resultPair = Pair.of(NextOperationType.UPDATE, resultMap);
        } else {
            resultPair = Pair.of(NextOperationType.SKIP_UPDATE, resultMap);
        }
        mapper.updateEntityFromDto(newReview, existedReview);
        existedReview = repository.save(existedReview);
        resultMap.put(ReviewAgeType.NEW, mapper.toDto(existedReview));
        return resultPair;
    }

    @Transactional
    @Override
    public ReviewResponseDto deleteById(UUID creatorId, UUID specialistId, UUID id) {
        ReviewEntity entity = repository.findById(id).orElseThrow(ReviewNotFoundByIdException::new );
        ReviewResponseDto dto = mapper.toDto(entity);
        this.assertOwnership(dto.creatorId(), creatorId);
        this.assertSpecialistAffiliation(entity.getSpecialist().getId(), specialistId);
        repository.deleteById(id);
        return dto;
    }

    @Transactional
    @Override
    public ReviewResponseDto deleteById(UUID specialistId, UUID id) {
        ReviewEntity entity = repository.findById(id).orElseThrow(ReviewNotFoundByIdException::new );
        this.assertSpecialistAffiliation(entity.getSpecialist().getId(), specialistId);
        ReviewResponseDto dto = mapper.toDto(entity);
        repository.deleteById(id);
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<ReviewResponseDto> findAllWithSortBySpecialistIdAndStatus(UUID specialistId,
                                                                                  ReviewStatus status,
                                                                                  ReviewSort sort) {
        Page<ReviewEntity> entityPage = repository.findAllBySpecialistIdAndStatus(
                specialistId,
                status,
                this.generatePageable(sort)
        );
        return new PageResponse<>(
                mapper.toDtoList(entityPage.getContent()),
                entityPage.getTotalPages()
        );
    }

    private void assertOwnership(UUID existedCreatorId, UUID creatorId) {
        if (!existedCreatorId.equals(creatorId)) {
            throw new OwnershipException();
        }
    }

    private void assertSpecialistAffiliation(UUID existedSpecialistId, UUID specialistId) {
        if (!existedSpecialistId.equals(specialistId)) {
            throw new NotAffiliatedToSpecialistException();
        }
    }

    private Pageable generatePageable(ReviewSort sort) {
        if (sort.getSortBy() != null) {
            if (sort.isAsc() != null && sort.isAsc()) {
                return PageRequest.of(
                        sort.getPageNumber(),
                        sort.getPageSize(),
                        Sort.by(sort.getSortBy().getColumnName()).ascending()
                );
            } else {
                return PageRequest.of(
                        sort.getPageNumber(),
                        sort.getPageSize(),
                        Sort.by(sort.getSortBy().getColumnName()).descending()
                );
            }
        } else {
            return PageRequest.of(sort.getPageNumber(), sort.getPageSize(), Sort.by("rating").descending());
        }
    }
}
