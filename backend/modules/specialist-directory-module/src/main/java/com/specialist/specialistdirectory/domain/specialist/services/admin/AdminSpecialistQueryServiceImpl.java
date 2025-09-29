package com.specialist.specialistdirectory.domain.specialist.services.admin;

import com.specialist.specialistdirectory.domain.specialist.mappers.FullSpecialistMapper;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.FullSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.repositories.PaginationUtils;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistSpecification;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecificationRepository;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistCountService;
import com.specialist.utils.pagination.PageResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminSpecialistQueryServiceImpl implements AdminSpecialistQueryService {

    private final SpecificationRepository<SpecialistEntity> repository;
    private final FullSpecialistMapper mapper;
    private final SpecialistCountService countService;

    public AdminSpecialistQueryServiceImpl(@Qualifier("fullSpecialistSpecificationRepository")
                                           SpecificationRepository<SpecialistEntity> repository,
                                           FullSpecialistMapper mapper, SpecialistCountService countService) {
        this.repository = repository;
        this.mapper = mapper;
        this.countService = countService;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<FullSpecialistResponseDto> findAll(AdminSpecialistFilter filter) {
        Specification<SpecialistEntity> specification = PaginationUtils.generateSpecification(filter)
                .and(SpecialistSpecification.filterByStatus(filter.getStatus()));
        Slice<SpecialistEntity> entityPage = repository.findAll(
                specification, PaginationUtils.generatePageable(filter)
        );
        return new PageResponse<>(
                mapper.toResponseDtoList(entityPage.getContent()),
                countService.countByAdminFilter(filter)
        );
    }
}
