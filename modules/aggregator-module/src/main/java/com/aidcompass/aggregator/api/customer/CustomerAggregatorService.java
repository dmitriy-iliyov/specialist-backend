package com.aidcompass.aggregator.api.customer;

import com.aidcompass.aggregator.AggregatorUtils;
import com.aidcompass.core.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.users.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.users.customer.services.CustomerService;
import com.aidcompass.core.general.contracts.dto.PageResponse;
import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerAggregatorService {

    private final CustomerService customerService;
    private final AggregatorUtils utils;


    public CustomerPrivateProfileDto findPrivateProfile(UUID id) {
        String url = utils.findAvatarUrlByOwnerId(id);
        PrivateCustomerResponseDto fullDto = customerService.findPrivateById(id);
        return new CustomerPrivateProfileDto(url, fullDto, utils.findAllPrivateContactByOwnerId(id));
    }

    @Transactional(noRollbackFor = BaseNotFoundException.class)
    public void delete(UUID id, String password, HttpServletRequest request, HttpServletResponse response) {
        utils.deleteAllUserAlignments(id, password, request, response);
        customerService.deleteById(id);
    }

    public PageResponse<CustomerPrivateProfileDto> findAllByNamesCombination(String firstName, String secondName,
                                                                              String lastName, int page, int size) {
        PageResponse<PrivateCustomerResponseDto> dtoPage = customerService.findAllByNamesCombination(
                firstName, secondName, lastName, page, size
        );
        Map<UUID, PrivateCustomerResponseDto> dtoMap = dtoPage.data().stream()
                .collect(Collectors.toMap(PrivateCustomerResponseDto::id, Function.identity()));
        Map<UUID, String> avatarMap = utils.findAllAvatarUrlByOwnerIdIn(dtoMap.keySet());
        Map<UUID, List<PrivateContactResponseDto>> contactsMap = utils.findAllPrivateContactByOwnerIdIn(dtoMap.keySet());

        return new PageResponse<>(
                dtoMap.entrySet().stream()
                        .map(dto -> new CustomerPrivateProfileDto(
                                avatarMap.get(dto.getKey()),
                                dto.getValue(),
                                contactsMap.get(dto.getKey()))
                        )
                        .toList(),
                dtoPage.totalPage()
        );
    }
}

