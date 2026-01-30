package com.specialist.specialistdirectory.ut.domain.review.services;

import com.specialist.picture.PictureStorage;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewDeleteRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateRequest;
import com.specialist.specialistdirectory.domain.review.services.ReviewManagementFacade;
import com.specialist.specialistdirectory.domain.review.services.ReviewManagementPictureDecorator;
import com.specialist.specialistdirectory.domain.review.services.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewManagementPictureDecoratorUnitTests {

    @Mock
    private ReviewManagementFacade delegate;

    @Mock
    private PictureStorage pictureStorage;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewManagementPictureDecorator decorator;

    @Test
    @DisplayName("UT: save() with picture should save picture and update url")
    void save_withPicture_shouldSaveAndUpdateUrl() {
        MultipartFile picture = mock(MultipartFile.class);
        ReviewCreateRequest request = new ReviewCreateRequest(UUID.randomUUID(), UUID.randomUUID(), "payload", picture);
        ReviewResponseDto dto = mock(ReviewResponseDto.class);
        UUID reviewId = UUID.randomUUID();
        String url = "url";

        when(delegate.save(request)).thenReturn(dto);
        when(dto.id()).thenReturn(reviewId);
        when(pictureStorage.save(picture, reviewId)).thenReturn(url);

        ReviewResponseDto result = decorator.save(request);

        assertEquals(dto, result);
        verify(delegate).save(request);
        verify(pictureStorage).save(picture, reviewId);
        verify(reviewService).updatePictureUrlById(reviewId, url);
    }

    @Test
    @DisplayName("UT: save() without picture should only delegate")
    void save_withoutPicture_shouldOnlyDelegate() {
        ReviewCreateRequest request = new ReviewCreateRequest(UUID.randomUUID(), UUID.randomUUID(), "payload", null);
        ReviewResponseDto dto = mock(ReviewResponseDto.class);

        when(delegate.save(request)).thenReturn(dto);

        ReviewResponseDto result = decorator.save(request);

        assertEquals(dto, result);
        verify(delegate).save(request);
        verifyNoInteractions(pictureStorage, reviewService);
    }

    @Test
    @DisplayName("UT: update() with picture should save picture and update url")
    void update_withPicture_shouldSaveAndUpdateUrl() {
        MultipartFile picture = mock(MultipartFile.class);
        ReviewUpdateRequest request = new ReviewUpdateRequest(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "payload", picture);
        ReviewResponseDto dto = mock(ReviewResponseDto.class);
        UUID reviewId = UUID.randomUUID();
        String url = "url";

        when(delegate.update(request)).thenReturn(dto);
        when(dto.id()).thenReturn(reviewId);
        when(pictureStorage.save(picture, reviewId)).thenReturn(url);

        ReviewResponseDto result = decorator.update(request);

        assertEquals(dto, result);
        verify(delegate).update(request);
        verify(pictureStorage).save(picture, reviewId);
        verify(reviewService).updatePictureUrlById(reviewId, url);
    }

    @Test
    @DisplayName("UT: update() without picture should only delegate")
    void update_withoutPicture_shouldOnlyDelegate() {
        ReviewUpdateRequest request = new ReviewUpdateRequest(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "payload", null);
        ReviewResponseDto dto = mock(ReviewResponseDto.class);

        when(delegate.update(request)).thenReturn(dto);

        ReviewResponseDto result = decorator.update(request);

        assertEquals(dto, result);
        verify(delegate).update(request);
        verifyNoInteractions(pictureStorage, reviewService);
    }

    @Test
    @DisplayName("UT: delete() should delegate and delete picture")
    void delete_shouldDelegateAndDeletePicture() {
        UUID reviewId = UUID.randomUUID();
        ReviewDeleteRequest request = new ReviewDeleteRequest(reviewId, UUID.randomUUID(), UUID.randomUUID());

        decorator.delete(request);

        verify(delegate).delete(request);
        verify(pictureStorage).deleteByAggregateId(reviewId);
    }
}
