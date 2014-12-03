package com.turbomanage.storm.apt.entity;

/**
 * Created by galex on 12/3/14.
 */
public interface PersistedField {

    String getFieldName();

    void setColName(String colName);

    String getJavaType();

    String getConverterName();

    String getQualifiedConverterClass();

    boolean isEntityId();

    boolean isEnum();

    void setEntityId(boolean isEntityId);

    public boolean isNullable();

    String getSetter();

    String getGetter();

    public String getBindType();

    public String getSqlType();
}
