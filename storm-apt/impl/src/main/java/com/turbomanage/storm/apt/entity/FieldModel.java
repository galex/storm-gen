/*******************************************************************************
 * Copyright 2012 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.turbomanage.storm.apt.entity;

import com.turbomanage.storm.apt.StringUtils;
import com.turbomanage.storm.apt.converter.ConverterModel;
import com.turbomanage.storm.types.TypeConverter.SqlType;

/**
 * Model of a persisted field
 *
 * @author David M. Chandler
 */
public class FieldModel implements PersistedField {

	private String fieldName, colName, javaType;
	private boolean isEnum, isEntityId;
	private ConverterModel converter;

	public FieldModel(String fieldName, String javaType, boolean isEnum, ConverterModel converter) {
		this.fieldName = fieldName;
		this.javaType = javaType;
		this.isEnum = isEnum;
		this.converter = converter;
		// TODO or @ColumnName
		this.colName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getColName() {
		// CursorAdapter requires lowercase _id col
		if (EntityModel.ID_COL.equals(colName)) {
			return EntityModel.ID_COL;
		} else {
			return colName.toUpperCase();
		}
	}
	
	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getJavaType() {
		return javaType;
	}

	private ConverterModel getConverter() {
		return this.converter;
	}

	/**
	 * Convenience method for more compact Dao templates. Returns the name of
	 * this field's converter class sans package name.
	 *
	 * @return
	 */
	public String getConverterName() {
		return getConverter().getClassName();
	}

	/**
	 * Fully-qualified name of the converter class for this field.
	 *
	 * @return String classname
	 */
	public String getQualifiedConverterClass() {
		return getConverter().getQualifiedClassName();
	}

	/**
	 * Morph bind type like INT ==> Int so it can be used in a Cursor getXxx
	 * method name. Never called at runtime.
	 *
	 * @return
	 */
	public String getBindType() {
		String bindType = getConverter().getBindType().name();
		return bindType.charAt(0) + bindType.toLowerCase().substring(1);
	}

	public String getSqlType() {
		if (this.isEntityId)
			return "INTEGER PRIMARY KEY AUTOINCREMENT";
		else if (isEnum) {
			return SqlType.TEXT.name();
		}
		return this.converter.getSqlType().name();
	}

	public String getSetter() {
		return "set" + StringUtils.capFirst(fieldName);
	}

	public String getGetter() {
		if ("boolean".equals(javaType))
			return "is" + StringUtils.capFirst(fieldName);
		else
			return "get" + StringUtils.capFirst(fieldName);
	}

	public boolean isNullable() {
		return javaType.contains(".") || javaType.contains("[]");
	}

	public boolean isEnum() {
		return isEnum;
	}

	public boolean isEntityId() {
		return isEntityId;
	}

	public void setEntityId(boolean isEntityId) {
		this.isEntityId = isEntityId;
	}
}
