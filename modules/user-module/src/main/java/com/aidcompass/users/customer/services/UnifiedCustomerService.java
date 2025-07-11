package com.aidcompass.users.customer.services;

import com.aidcompass.users.customer.mapper.CustomerMapper;
import com.aidcompass.users.customer.models.CustomerDto;
import com.aidcompass.users.customer.models.CustomerEntity;
import com.aidcompass.users.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.users.customer.models.PublicCustomerResponseDto;
import com.aidcompass.users.customer.repository.CustomerRepository;
import com.aidcompass.users.customer.repository.CustomerSpecifications;
import com.aidcompass.users.detail.models.DetailEntity;
import com.aidcompass.core.general.contracts.dto.PageResponse;
import com.aidcompass.core.general.contracts.enums.ServiceType;
import com.aidcompass.users.general.exceptions.customer.CustomerNotFoundByIdException;
import com.aidcompass.users.general.exceptions.customer.CustomerNotFoundByUserIdException;
import com.aidcompass.users.profile_status.ProfileConfig;
import com.aidcompass.users.profile_status.ProfileStatusService;
import com.aidcompass.users.profile_status.ProfileStatusUpdateService;
import com.aidcompass.users.profile_status.models.ProfileStatus;
import com.aidcompass.users.profile_status.models.ProfileStatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnifiedCustomerService implements CustomerService, ProfileStatusUpdateService {

    private final ServiceType type = ServiceType.CUSTOMER_SERVICE;
    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final ProfileStatusService profileStatusService;


    @Override
    public ServiceType getType() {
        return type;
    }

    @Transactional
    @Override
    public void save(UUID id, DetailEntity detailEntity) {
        ProfileStatusEntity statusEntity = profileStatusService.findByStatus(ProfileStatus.INCOMPLETE);
        repository.save(new CustomerEntity(id, statusEntity, detailEntity));
    }

    @CachePut(value = "customers:private", key="#id")
    @Transactional
    @Override
    public PrivateCustomerResponseDto save(UUID id, DetailEntity detailEntity, CustomerDto dto) {
        CustomerEntity customerEntity = mapper.toEntity(id, dto);
        ProfileStatusEntity statusEntity = profileStatusService.findByStatus(ProfileStatus.INCOMPLETE);
        customerEntity.setProfileProgress(ProfileConfig.PROFILE_PROGRESS_STEP);
        customerEntity.setProfileStatusEntity(statusEntity);
        customerEntity.setDetailEntity(detailEntity);
        return mapper.toPrivateDto(repository.save(customerEntity));
    }

    @Transactional(readOnly = true)
    @Override
    public PublicCustomerResponseDto findPublicById(UUID id) {
        CustomerEntity customerEntity = repository.findById(id).orElseThrow(
                CustomerNotFoundByIdException::new
        );
        return mapper.toPublicDto(customerEntity);
    }

    @Cacheable(value = "customers:private", key="#id")
    @Transactional(readOnly = true)
    @Override
    public PrivateCustomerResponseDto findPrivateById(UUID id) {
        CustomerEntity entity = repository.findById(id).orElseThrow(CustomerNotFoundByUserIdException::new);
        return mapper.toPrivateDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PublicCustomerResponseDto> findAllByIds(Set<UUID> ids) {
        return mapper.toPublicDtoList(repository.findAllById(ids));
    }

    @CachePut(value = "customers:private", key="#id")
    @Transactional
    @Override
    public PrivateCustomerResponseDto updateById(UUID id, CustomerDto dto) {
        CustomerEntity customerEntity = repository.findById(id).orElseThrow(CustomerNotFoundByIdException::new);
        mapper.updateEntityFromDto(dto, customerEntity);
        return mapper.toPrivateDto(repository.save(customerEntity));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int updateProfileStatus(UUID id) {
        CustomerEntity entity = repository.findWithProfileStatusById(id).orElseThrow(CustomerNotFoundByIdException::new);
        int newProgress = entity.getProfileProgress() + ProfileConfig.PROFILE_PROGRESS_STEP;
        if (newProgress == ProfileConfig.MAX_CUSTOMER_PROFILE_PROGRESS) {
            ProfileStatusEntity profileStatus = profileStatusService.findByStatus(ProfileStatus.COMPLETE);
            repository.updateProgressAndProfileStatus(id, ProfileConfig.PROFILE_PROGRESS_STEP, profileStatus);
        } else if (newProgress < ProfileConfig.MAX_CUSTOMER_PROFILE_PROGRESS) {
            repository.updateProfileProgress(id, ProfileConfig.PROFILE_PROGRESS_STEP);
        }
        return newProgress;
    }

    @Cacheable(value = "customers:count")
    @Transactional(readOnly = true)
    @Override
    public long count() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<PrivateCustomerResponseDto> findAllByNamesCombination(String firstName, String secondName,
                                                                              String lastName, int page, int size) {
        Specification<CustomerEntity> specification = Specification
                .where(CustomerSpecifications.hasFirstName(firstName))
                .and(CustomerSpecifications.hasSecondName(secondName))
                .and(CustomerSpecifications.hasLastName(lastName));
        Page<CustomerEntity> entityPage = repository.findAll(specification, PageRequest.of(page, size));
        return new PageResponse<>(
                mapper.toPrivateDtoList(entityPage.getContent()),
                entityPage.getTotalPages()
        );
    }

    @CacheEvict(value = "customers:private", key="#id")
    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
