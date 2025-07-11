package com.aidcompass.users.jurist.specialization;

import com.aidcompass.users.general.exceptions.jurist.JuristTypeNotFoundByTypeException;
import com.aidcompass.users.jurist.specialization.mapper.JuristTypeMapper;
import com.aidcompass.users.jurist.specialization.models.JuristType;
import com.aidcompass.users.jurist.specialization.models.JuristTypeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JuristTypeService {

    private final JuristTypeRepository repository;
    private final JuristTypeMapper mapper;


    @Transactional
    public void saveAll(List<JuristType> typeList) {
        List<JuristTypeEntity> entityList = mapper.toEntityList(typeList);
        repository.saveAll(entityList);
    }

    @Transactional(readOnly = true)
    public JuristTypeEntity findEntityByType(JuristType type) {
        if (type == null) {
            return null;
        }
        return repository.findByType(type).orElseThrow(JuristTypeNotFoundByTypeException::new);
    }
}
