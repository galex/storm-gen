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
package com.turbomanage.storm.apt.entity;

import com.turbomanage.storm.SQLiteDao;
import com.turbomanage.storm.api.Entity;
import com.turbomanage.storm.apt.BaseDaoModel;
import com.turbomanage.storm.apt.ClassModel;
import com.turbomanage.storm.apt.database.DatabaseModel;


public class EntityModel extends ClassModel {

	private static final String TABLE_SUFFIX = "Table";
	static final String DEFAULT_ID_FIELD = "id";
	static final String ID_COL = "_id";
	private BaseDaoModel baseDaoClass;
	private DatabaseModel dbModel;
	private String dbName;
	private String tableName;

	public EntityModel(Entity entity) {
		this.setTableName(entity.name());
		this.setDbName(entity.dbName());
	}
	
	public EntityModel(javax.persistence.Entity entity) {
		this.setTableName(entity.name());
	}

	public String getEntityName() {
		return this.getClassName();
	}
	
	public String getDaoName() {
		return this.getEntityName() + "Dao";
	}
	
	public String getDaoPackage() {
		return this.getPackage() + ".dao";
	}

	public String getDbFactory() {
		return dbModel.getFactoryClass();
	}
	
	/**
	 * Provides the simple name of the base DAO class to templates.
	 * 
	 * @return String Simple name of the base DAO
	 */
	public String getBaseDaoName() {
		return this.baseDaoClass.getClassName();
	}

	protected BaseDaoModel getBaseDaoClass() {
		return baseDaoClass;
	}

	protected void setBaseDaoClass(BaseDaoModel daoClass) {
		this.baseDaoClass = daoClass;
		// add corresponding import
		this.addImport(daoClass.getQualifiedClassName());
	}

	void setDatabase(DatabaseModel dbModel) {
		this.dbModel = dbModel;
		dbModel.addEntity(this);
	}
	
	public String getTableHelperClass() {
		return getDaoPackage() + "." + getTableHelperName();
	}
	
	public String getTableHelperName() {
		return capFirst(getTableName().replace("[", "").replace("]", "") + TABLE_SUFFIX);
	}

	public String getTableName() {
		return tableName;
	}

	void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public PersistedField getIdField() {
		for (PersistedField f : this.fields) {
			if (f.isEntityId()) {
				return f;
			}
		}
		return null;
	}

	void setIdField(PersistedField idField) {
		idField.setEntityId(true);
		// TODO use a view instead
		idField.setColName("_id");
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}
