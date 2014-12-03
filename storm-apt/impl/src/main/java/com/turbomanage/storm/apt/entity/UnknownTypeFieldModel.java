package com.turbomanage.storm.apt.entity;

import com.turbomanage.storm.apt.StringUtils;
import com.turbomanage.storm.types.TypeConverter;

public class UnknownTypeFieldModel implements PersistedField {

    private String fieldName, javaType;

    public UnknownTypeFieldModel(String fieldName, String javaType) {

        this.fieldName = fieldName;
        this.javaType = javaType;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public void setColName(String colName) {
        // This field will never be an _id field
    }

    @Override
    public String getJavaType() {
        return String.class.getSimpleName();
    }

    @Override
    public String getConverterName() {
        return null;
    }

    @Override
    public String getQualifiedConverterClass() {
        return null;
    }

    @Override
    public boolean isEntityId() {
        return false;
    }

    @Override
    public boolean isEnum() {
        return false;
    }

    @Override
    public void setEntityId(boolean isEntityId) {

    }

    @Override
    public boolean isNullable() {
        return true;
    }

    @Override
    public String getSetter() {
        return "set" + StringUtils.capFirst(fieldName);
    }

    @Override
    public String getGetter() {
        return "get" + StringUtils.capFirst(fieldName);
    }

    @Override
    public String getBindType() {
        return getJavaType();
    }

    @Override
    public String getSqlType() {
        return TypeConverter.SqlType.TEXT.name();
    }
}