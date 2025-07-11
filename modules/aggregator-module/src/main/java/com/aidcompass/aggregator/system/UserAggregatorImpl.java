package com.aidcompass.aggregator.system;

import com.aidcompass.aggregator.AggregatorUtils;
import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.core.contact.core.services.ContactService;
import com.aidcompass.users.customer.models.PublicCustomerResponseDto;
import com.aidcompass.users.customer.services.CustomerService;
import com.aidcompass.users.doctor.models.dto.PublicDoctorResponseDto;
import com.aidcompass.users.doctor.services.DoctorService;
import com.aidcompass.message.information.dto.UserDto;
import com.aidcompass.users.jurist.models.dto.PublicJuristResponseDto;
import com.aidcompass.users.jurist.services.JuristService;
import com.aidcompass.aggregator.system.exception.UnsupportedUserTypeException;
import com.aidcompass.aggregator.system.models.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAggregatorImpl implements UserAggregator {

    private final CustomerService customerService;
    private final DoctorService doctorService;
    private final JuristService juristService;
    private final ContactService contactService;
    private final AggregatorUtils utils;


    @Override
    public UserDto findUserByIdAndType(UUID id, UserType type) {
        PublicContactResponseDto contactDto = findPrimaryContactByOwnerId(id);
        if (contactDto != null) {
            switch (type){
                case CUSTOMER -> {
                    PublicCustomerResponseDto dto = customerService.findPublicById(id);
                    return new UserDto(dto.id(), dto.firstName(), dto.secondName(), dto.lastName(), contactDto.type(), contactDto.contact());
                }
                case DOCTOR -> {
                    PublicDoctorResponseDto dto = doctorService.findPublicById(id);
                    return new UserDto(dto.id(), dto.firstName(), dto.secondName(), dto.lastName(), contactDto.type(), contactDto.contact());
                }
                case JURIST -> {
                    PublicJuristResponseDto dto = juristService.findPublicById(id);
                    return new UserDto(dto.id(), dto.firstName(), dto.secondName(), dto.lastName(), contactDto.type(), contactDto.contact());
                }
                default -> throw new UnsupportedUserTypeException();
            }
        }
        return null;
    }

    @Override
    public Map<UUID, UserDto> findAllCustomerByIdIn(Set<UUID> ids) {
        List<PublicCustomerResponseDto> customerList = customerService.findAllByIds(ids);
        Map<UUID, SystemContactDto> contactMap = utils.findPrimaryContactByOwnerIdIn(ids);
        return customerList.stream()
                .collect(
                        Collectors.toMap(
                            PublicCustomerResponseDto::id,
                            dto -> {
                                SystemContactDto contact = contactMap.get(dto.id());
                                return new UserDto(
                                        dto.id(),
                                        dto.firstName(),
                                        dto.secondName(),
                                        dto.lastName(),
                                        contact.getType(),
                                        contact.getContact()
                                );
                            })
                );
    }

    @Override
    public PublicContactResponseDto findPrimaryContactByOwnerId(UUID id) {
        return contactService.findPrimaryByOwnerId(id)
                .stream()
                .filter(dto -> dto.type().equals(ContactType.EMAIL))
                .findFirst().orElse(null);
    }
}
