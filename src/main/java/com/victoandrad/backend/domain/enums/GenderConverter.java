package com.victoandrad.backend.domain.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        return gender != null ? gender.getId() : null;
    }

    @Override
    public Gender convertToEntityAttribute(Integer id) {
        return id != null ? Gender.fromId(id) : null;
    }
}
