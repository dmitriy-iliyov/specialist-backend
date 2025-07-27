package com.aidcompass.user.services;

import com.aidcompass.contracts.user.CreatorRatingUpdateEvent;
import com.aidcompass.user.models.RatingUpdateEventEntity;
import com.aidcompass.user.repositories.RatingUpdateEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingUpdateEventServiceImpl implements RatingUpdateEventService {

    @Value("${api.review-buffer.clean.batch-size}")
    public int pageSize;
    private final RatingUpdateEventRepository repository;


    @CachePut(value = "users:events:creator-rating-update:exists", key = "#event.id()")
    @Transactional
    @Override
    public void save(CreatorRatingUpdateEvent event) {

    }

    @Cacheable(value = "users:events:creator-rating-update:exists", key = "#id", condition = "#result == true")
    @Transactional(readOnly = true)
    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Scheduled(cron = "")
    @CacheEvict(value = "users:events:creator-rating-update:exists", allEntries = true)
    @Transactional
    @Override
    public void clean() {
        Slice<RatingUpdateEventEntity> entitySlice = repository.findAll(Pageable.ofSize(pageSize));
        
    }
}
