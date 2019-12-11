package com.example.taskmanager.model.Database.greenDao;

import com.example.taskmanager.model.StateEnum;

import org.greenrobot.greendao.converter.PropertyConverter;

public class EnumConverter implements PropertyConverter<StateEnum, String> {
    @Override
    public StateEnum convertToEntityProperty(String databaseValue) {
        if (databaseValue == null)
            return null;
        else if (databaseValue.equals(StateEnum.TO_DO.toString()))
            return StateEnum.TO_DO;
        else if (databaseValue.equals(StateEnum.DOING.toString()))
            return StateEnum.DOING;
        else
            return StateEnum.DONE;
    }

    @Override
    public String convertToDatabaseValue(StateEnum entityProperty) {
        if (entityProperty == null)
            return null;

        return entityProperty.toString();
    }
}
