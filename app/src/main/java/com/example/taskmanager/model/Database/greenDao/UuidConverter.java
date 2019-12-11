package com.example.taskmanager.model.Database.greenDao;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.UUID;

public class UuidConverter implements PropertyConverter<UUID, String> {

    @Override
    public UUID convertToEntityProperty(String databaseValue) {
        if (databaseValue == null)
            return null;

        return UUID.fromString(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(UUID entityProperty) {
        if (entityProperty == null)
            return null;

        return entityProperty.toString();
    }
}
