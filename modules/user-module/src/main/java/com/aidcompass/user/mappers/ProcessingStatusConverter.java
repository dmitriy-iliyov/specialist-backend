package com.aidcompass.user.mappers;

import com.aidcompass.user.models.enums.ProcessingStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProcessingStatusConverter implements AttributeConverter<ProcessingStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProcessingStatus processingStatus) {
        return processingStatus.getCode();
    }

    @Override
    public ProcessingStatus convertToEntityAttribute(Integer integer) {
        return ProcessingStatus.fromCode(integer);
    }
}
