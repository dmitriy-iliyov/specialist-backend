package com.aidcompass.specialistdirectory.domain.review.services;

import com.aidcompass.specialistdirectory.domain.review.ReviewMapper;
import com.aidcompass.specialistdirectory.domain.review.ReviewRepository;
import com.aidcompass.specialistdirectory.domain.review.models.ReviewEntity;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.aidcompass.specialistdirectory.domain.review.models.enums.NextOperation;
import com.aidcompass.specialistdirectory.domain.review.models.enums.ReviewAge;
import com.aidcompass.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.aidcompass.specialistdirectory.domain.review.services.interfases.ReviewService;
import com.aidcompass.specialistdirectory.exceptions.NotAffiliatedToSpecialistException;
import com.aidcompass.specialistdirectory.exceptions.OwnershipException;
import com.aidcompass.specialistdirectory.exceptions.ReviewNotFoundByIdException;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;
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
    public ReviewResponseDto save(ReviewCreateDto dto) {
        ReviewEntity entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    @Override
    public Pair<NextOperation, Map<ReviewAge, ReviewResponseDto>> update(ReviewUpdateDto newReview) {
        Pair<NextOperation, Map<ReviewAge, ReviewResponseDto>> resultPair;
        Map<ReviewAge, ReviewResponseDto> resultMap = new HashMap<>();

        ReviewEntity existedReview = repository.findById(newReview.getId()).orElseThrow(ReviewNotFoundByIdException::new);

        this.assertOwnership(existedReview.getCreatorId(), newReview.getCreatorId());
        this.assertSpecialistAffiliation(existedReview.getSpecialist().getId(), newReview.getSpecialistId());

        resultMap.put(ReviewAge.OLD, mapper.toDto(existedReview));
        if (existedReview.getRating() != newReview.getRating()) {
            existedReview.setRating(newReview.getRating());
            resultPair = Pair.of(NextOperation.UPDATE_RATING, resultMap);
        } else {
            resultPair = Pair.of(NextOperation.SKIP_UPDATE_RATING, resultMap);
        }
        mapper.updateEntityFromDto(newReview, existedReview);
        existedReview = repository.save(existedReview);
        resultMap.put(ReviewAge.NEW, mapper.toDto(existedReview));
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
        ReviewResponseDto dto = mapper.toDto(entity);

        this.assertSpecialistAffiliation(entity.getSpecialist().getId(), specialistId);

        repository.deleteById(id);
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<ReviewResponseDto> findAllWithSortBySpecialistId(UUID specialistId, ReviewSort sort) {
        Page<ReviewEntity> entityPage = repository.findAllBySpecialistId(specialistId, this.generatePageable(sort));
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
        if (sort.sortBy() != null) {
            if (sort.asc() != null && sort.asc()) {
                return PageRequest.of(
                        sort.pageNumber(),
                        sort.pageSize(),
                        Sort.by(sort.sortBy().getColumnName()).ascending()
                );
            } else {
                return PageRequest.of(
                        sort.pageNumber(),
                        sort.pageSize(),
                        Sort.by(sort.sortBy().getColumnName()).descending()
                );
            }
        } else {
            return PageRequest.of(sort.pageNumber(), sort.pageSize(), Sort.by("rating").descending());
        }
    }
}
