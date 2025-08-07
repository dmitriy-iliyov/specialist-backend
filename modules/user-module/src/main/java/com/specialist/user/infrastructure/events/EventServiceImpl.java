package com.specialist.user.infrastructure.events;

import com.specialist.contracts.user.CreatorRatingUpdateEvent;
import com.specialist.user.exceptions.RatingUpdateEventEntityNotFoundByIdException;
import com.specialist.user.mappers.RatingUpdateEventMapper;
import com.specialist.user.models.RatingUpdateEventEntity;
import com.specialist.user.models.enums.ProcessingStatus;
import com.specialist.user.repositories.RatingUpdateEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final RatingUpdateEventMapper mapper;
    private final RatingUpdateEventRepository repository;


    @CachePut(value = "users:events:creator-rating-update:processed", key = "#event.id()",
              condition = "#status == com.aidcompass.user.models.enums.ProcessingStatus.PROCESSED")
    @Transactional
    @Override
    public void saveOrUpdate(ProcessingStatus status, CreatorRatingUpdateEvent event) {
        RatingUpdateEventEntity entity = repository.findById(event.id()).orElse(null);
        if (entity == null) {
            entity = mapper.toEntity(event);
        }
        entity.setStatus(status);
        repository.save(entity);
    }

    @Cacheable(value = "users:events:creator-rating-update:processed", key = "#id", condition = "#result == true")
    @Transactional(readOnly = true)
    @Override
    public boolean isProcessed(UUID id) {
        boolean isExists = repository.existsById(id);
        if (!isExists) {
            return false;
        }
        RatingUpdateEventEntity entity = repository.findById(id).orElseThrow(
                RatingUpdateEventEntityNotFoundByIdException::new
        );
        return entity.getStatus().equals(ProcessingStatus.PROCESSED);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<UUID> findBatchByStatus(ProcessingStatus status, int batchSize) {
        return repository.findAllByStatus(status, Pageable.ofSize(batchSize))
                .getContent().stream()
                .map(RatingUpdateEventEntity::getId)
                .collect(Collectors.toSet());
    }

    @CacheEvict(value = "users:events:creator-rating-update:processed", allEntries = true)
    @Transactional
    @Override
    public void deleteBatchByIds(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return;
        }
        repository.deleteAllByIdInBatch(ids);
    }
}
