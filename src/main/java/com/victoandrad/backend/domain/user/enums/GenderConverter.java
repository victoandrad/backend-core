package com.victoandrad.backend.domain.user.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, Integer> {

    // ==============================
    // METHODS
    // ==============================

    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        return gender != null ? gender.getId() : null;
    }

    // ==============================

    @Override
    public Gender convertToEntityAttribute(Integer id) {
        return id != null ? Gender.fromId(id) : null;
    }
}