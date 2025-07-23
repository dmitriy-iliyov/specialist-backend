package com.aidcompass.specialistdirectory.domain.review.services;

import com.aidcompass.avatar.AvatarService;
import com.aidcompass.users.customer.models.PublicCustomerResponseDto;
import com.aidcompass.users.customer.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewUserAggregatorImpl implements ReviewUserAggregator {

    private final CustomerService customerService;
    private final AvatarService avatarService;


    @Override
    public Map<UUID, String> findAvatarsByIdIn(Set<UUID> userIds) {
        return avatarService.findAllUrlByOwnerIdIn(userIds);
    }

    @Override
    public Map<UUID, String> findUsernamesByIdIn(Set<UUID> userIds) {
        return customerService.findAllByIds(userIds).stream()
                .collect(Collectors.toMap(
                        PublicCustomerResponseDto::id,
                        customer -> customer.lastName() + " " + customer.firstName() + " " + customer.secondName())
                );
    }
}
