package com.specialist.specialistdirectory.ut.domain.review.services;

import com.specialist.specialistdirectory.domain.review.mappers.ReviewMapper;
import com.specialist.specialistdirectory.domain.review.models.ReviewEntity;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.specialistdirectory.domain.review.models.enums.NextOperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewAgeType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewStatus;
import com.specialist.specialistdirectory.domain.review.models.enums.SortType;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.specialistdirectory.domain.review.repositories.ReviewRepository;
import com.specialist.specialistdirectory.domain.review.services.ReviewServiceImpl;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.exceptions.NotAffiliatedToSpecialistException;
import com.specialist.specialistdirectory.exceptions.OwnershipException;
import com.specialist.specialistdirectory.exceptions.ReviewNotFoundByIdException;
import com.specialist.utils.pagination.PageResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplUnitTests {

    @Mock
    private ReviewRepository repository;

    @Mock
    private ReviewMapper mapper;

    @InjectMocks
    private ReviewServiceImpl service;

    @Test
    @DisplayName("UT: save() should set specialist, status and save")
    void save_shouldSetSpecialistAndSave() {
        SpecialistEntity specialist = new SpecialistEntity();
        ReviewCreateDto dto = new ReviewCreateDto(UUID.randomUUID(), UUID.randomUUID(), "text", 5);
        ReviewEntity entity = new ReviewEntity();
        ReviewResponseDto responseDto = mock(ReviewResponseDto.class);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(responseDto);

        ReviewResponseDto result = service.save(specialist, dto);

        assertEquals(responseDto, result);
        assertEquals(specialist, entity.getSpecialist());
        assertEquals(ReviewStatus.UNAPPROVED, entity.getStatus());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: updatePictureUrlById() should call repository")
    void updatePictureUrlById_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        String url = "http://example.com/image.jpg";

        service.updatePictureUrlById(id, url);

        verify(repository).updatePictureUrlById(id, url);
    }

    @Test
    @DisplayName("UT: approve() when review exists should approve")
    void approve_shouldApprove() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ReviewEntity entity = new ReviewEntity();
        entity.setSpecialist(new SpecialistEntity());
        entity.getSpecialist().setId(specialistId);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.approve(specialistId, id, ApproverType.ADMIN);

        assertEquals(ReviewStatus.APPROVED, entity.getStatus());
        assertEquals(ApproverType.ADMIN, entity.getApprover());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: approve() when review not found should throw ReviewNotFoundByIdException")
    void approve_notFound_shouldThrow() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundByIdException.class, () -> service.approve(specialistId, id, ApproverType.ADMIN));
    }

    @Test
    @DisplayName("UT: approve() when not affiliated should throw exception")
    void approve_notAffiliated_shouldThrow() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ReviewEntity entity = new ReviewEntity();
        entity.setSpecialist(new SpecialistEntity());
        entity.getSpecialist().setId(UUID.randomUUID()); // Different specialist

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        assertThrows(NotAffiliatedToSpecialistException.class, () -> service.approve(specialistId, id, ApproverType.ADMIN));
    }

    @Test
    @DisplayName("UT: update() when rating changed should return UPDATE")
    void update_ratingChanged_shouldReturnUpdate() {
        ReviewUpdateDto dto = new ReviewUpdateDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "text", 4);
        ReviewEntity existedReview = new ReviewEntity();
        existedReview.setCreatorId(dto.creatorId());
        existedReview.setSpecialist(new SpecialistEntity());
        existedReview.getSpecialist().setId(dto.specialistId());
        existedReview.setRating(5);

        when(repository.findById(dto.id())).thenReturn(Optional.of(existedReview));
        when(repository.save(existedReview)).thenReturn(existedReview);
        when(mapper.toDto(existedReview)).thenReturn(mock(ReviewResponseDto.class));

        Pair<NextOperationType, Map<ReviewAgeType, ReviewResponseDto>> result = service.update(dto);

        assertEquals(NextOperationType.UPDATE, result.getLeft());
        verify(repository).save(existedReview);
    }

    @Test
    @DisplayName("UT: update() when rating not changed should return SKIP_UPDATE")
    void update_ratingNotChanged_shouldReturnSkipUpdate() {
        ReviewUpdateDto dto = new ReviewUpdateDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "text", 5);
        ReviewEntity existedReview = new ReviewEntity();
        existedReview.setCreatorId(dto.creatorId());
        existedReview.setSpecialist(new SpecialistEntity());
        existedReview.getSpecialist().setId(dto.specialistId());
        existedReview.setRating(5);

        when(repository.findById(dto.id())).thenReturn(Optional.of(existedReview));
        when(repository.save(existedReview)).thenReturn(existedReview);
        when(mapper.toDto(existedReview)).thenReturn(mock(ReviewResponseDto.class));

        Pair<NextOperationType, Map<ReviewAgeType, ReviewResponseDto>> result = service.update(dto);

        assertEquals(NextOperationType.SKIP_UPDATE, result.getLeft());
        verify(repository).save(existedReview);
    }

    @Test
    @DisplayName("UT: update() when review not found should throw ReviewNotFoundByIdException")
    void update_notFound_shouldThrow() {
        ReviewUpdateDto dto = new ReviewUpdateDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "text", 5);
        when(repository.findById(dto.id())).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundByIdException.class, () -> service.update(dto));
    }

    @Test
    @DisplayName("UT: update() when not owner should throw OwnershipException")
    void update_notOwner_shouldThrowException() {
        ReviewUpdateDto dto = new ReviewUpdateDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "text", 5);
        ReviewEntity existedReview = new ReviewEntity();
        existedReview.setCreatorId(UUID.randomUUID()); // Different creator

        when(repository.findById(dto.id())).thenReturn(Optional.of(existedReview));

        assertThrows(OwnershipException.class, () -> service.update(dto));
    }

    @Test
    @DisplayName("UT: update() when not affiliated should throw NotAffiliatedToSpecialistException")
    void update_notAffiliated_shouldThrow() {
        ReviewUpdateDto dto = new ReviewUpdateDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "text", 5);
        ReviewEntity existedReview = new ReviewEntity();
        existedReview.setCreatorId(dto.creatorId());
        existedReview.setSpecialist(new SpecialistEntity());
        existedReview.getSpecialist().setId(UUID.randomUUID()); // Different specialist

        when(repository.findById(dto.id())).thenReturn(Optional.of(existedReview));

        assertThrows(NotAffiliatedToSpecialistException.class, () -> service.update(dto));
    }

    @Test
    @DisplayName("UT: deleteById() with creator should delete")
    void deleteById_withCreator_shouldDelete() {
        UUID creatorId = UUID.randomUUID();
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ReviewEntity entity = mock(ReviewEntity.class);
        SpecialistEntity specialist = new SpecialistEntity();
        specialist.setId(specialistId);
        ReviewResponseDto dto = mock(ReviewResponseDto.class);

        when(entity.getSpecialist()).thenReturn(specialist);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        when(dto.creatorId()).thenReturn(creatorId);

        service.deleteById(creatorId, specialistId, id);

        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("UT: deleteById() with creator when not found should throw ReviewNotFoundByIdException")
    void deleteById_withCreator_notFound_shouldThrow() {
        UUID creatorId = UUID.randomUUID();
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundByIdException.class, () -> service.deleteById(creatorId, specialistId, id));
    }

    @Test
    @DisplayName("UT: deleteById() with creator when not owner should throw OwnershipException")
    void deleteById_withCreator_notOwner_shouldThrow() {
        UUID creatorId = UUID.randomUUID();
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ReviewEntity entity = mock(ReviewEntity.class);
        ReviewResponseDto dto = mock(ReviewResponseDto.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        when(dto.creatorId()).thenReturn(UUID.randomUUID()); // Different creator

        assertThrows(OwnershipException.class, () -> service.deleteById(creatorId, specialistId, id));
    }

    @Test
    @DisplayName("UT: deleteById() with creator when not affiliated should throw NotAffiliatedToSpecialistException")
    void deleteById_withCreator_notAffiliated_shouldThrow() {
        UUID creatorId = UUID.randomUUID();
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ReviewEntity entity = mock(ReviewEntity.class);
        SpecialistEntity specialist = new SpecialistEntity();
        specialist.setId(UUID.randomUUID()); // Different specialist
        ReviewResponseDto dto = mock(ReviewResponseDto.class);

        when(entity.getSpecialist()).thenReturn(specialist);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        when(dto.creatorId()).thenReturn(creatorId);

        assertThrows(NotAffiliatedToSpecialistException.class, () -> service.deleteById(creatorId, specialistId, id));
    }

    @Test
    @DisplayName("UT: deleteById() without creator should delete")
    void deleteById_withoutCreator_shouldDelete() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ReviewEntity entity = new ReviewEntity();
        entity.setSpecialist(new SpecialistEntity());
        entity.getSpecialist().setId(specialistId);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(mock(ReviewResponseDto.class));

        service.deleteById(specialistId, id);

        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("UT: deleteById() without creator when not found should throw ReviewNotFoundByIdException")
    void deleteById_withoutCreator_notFound_shouldThrow() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundByIdException.class, () -> service.deleteById(specialistId, id));
    }

    @Test
    @DisplayName("UT: deleteById() when not affiliated should throw NotAffiliatedToSpecialistException")
    void deleteById_notAffiliated_shouldThrowException() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ReviewEntity entity = new ReviewEntity();
        entity.setSpecialist(new SpecialistEntity());
        entity.getSpecialist().setId(UUID.randomUUID()); // Different specialist

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        assertThrows(NotAffiliatedToSpecialistException.class, () -> service.deleteById(specialistId, id));
    }

    @Test
    @DisplayName("UT: findAllWithSortBySpecialistIdAndStatus() should return mapped page")
    void findAll_shouldReturnMappedPage() {
        UUID specialistId = UUID.randomUUID();
        ReviewSort sort = new ReviewSort(0, 10, true, null);
        ReviewEntity entity = new ReviewEntity();
        Page<ReviewEntity> page = new PageImpl<>(List.of(entity));
        ReviewResponseDto dto = new ReviewResponseDto(UUID.randomUUID(), UUID.randomUUID(), "text", 5, null, LocalDateTime.now());

        when(repository.findAllBySpecialistIdAndStatus(eq(specialistId), eq(ReviewStatus.APPROVED), any(Pageable.class))).thenReturn(page);
        when(mapper.toDtoList(List.of(entity))).thenReturn(List.of(dto));

        PageResponse<ReviewResponseDto> result = service.findAllWithSortBySpecialistIdAndStatus(specialistId, ReviewStatus.APPROVED, sort);

        assertEquals(1, result.data().size());
        assertEquals(dto, result.data().get(0));
    }

    @Test
    @DisplayName("UT: findAllWithSortBySpecialistIdAndStatus() with sort ASC")
    void findAll_withSortAsc() {
        UUID specialistId = UUID.randomUUID();
        ReviewSort sort = new ReviewSort(0, 10, true, SortType.r);
        Page<ReviewEntity> page = new PageImpl<>(List.of());

        when(repository.findAllBySpecialistIdAndStatus(eq(specialistId), eq(ReviewStatus.APPROVED), any(Pageable.class))).thenAnswer(invocation -> {
            Pageable pageable = invocation.getArgument(2);
            assertEquals(Sort.by("rating").ascending(), pageable.getSort());
            return page;
        });

        service.findAllWithSortBySpecialistIdAndStatus(specialistId, ReviewStatus.APPROVED, sort);
    }

    @Test
    @DisplayName("UT: findAllWithSortBySpecialistIdAndStatus() with sort DESC")
    void findAll_withSortDesc() {
        UUID specialistId = UUID.randomUUID();
        ReviewSort sort = new ReviewSort(0, 10, false, SortType.r);
        Page<ReviewEntity> page = new PageImpl<>(List.of());

        when(repository.findAllBySpecialistIdAndStatus(eq(specialistId), eq(ReviewStatus.APPROVED), any(Pageable.class))).thenAnswer(invocation -> {
            Pageable pageable = invocation.getArgument(2);
            assertEquals(Sort.by("rating").descending(), pageable.getSort());
            return page;
        });

        service.findAllWithSortBySpecialistIdAndStatus(specialistId, ReviewStatus.APPROVED, sort);
    }

    @Test
    @DisplayName("UT: findAllWithSortBySpecialistIdAndStatus() with default sort")
    void findAll_withDefaultSort() {
        UUID specialistId = UUID.randomUUID();
        ReviewSort sort = new ReviewSort(0, 10, null, null);
        Page<ReviewEntity> page = new PageImpl<>(List.of());

        when(repository.findAllBySpecialistIdAndStatus(eq(specialistId), eq(ReviewStatus.APPROVED), any(Pageable.class))).thenAnswer(invocation -> {
            Pageable pageable = invocation.getArgument(2);
            assertEquals(Sort.by("rating").descending(), pageable.getSort());
            return page;
        });

        service.findAllWithSortBySpecialistIdAndStatus(specialistId, ReviewStatus.APPROVED, sort);
    }
}