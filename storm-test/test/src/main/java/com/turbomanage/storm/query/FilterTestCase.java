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
package com.turbomanage.storm.query;

import java.util.List;

import android.content.Context;
import android.test.AndroidTestCase;

import com.turbomanage.storm.entity.SimpleEntity;
import com.turbomanage.storm.entity.SimpleEntity.EnumType;
import com.turbomanage.storm.entity.ValueEntity;
import com.turbomanage.storm.entity.dao.SimpleEntityDao;
import com.turbomanage.storm.entity.dao.SimpleEntityTable;
import com.turbomanage.storm.entity.dao.SimpleEntityTable.Columns;
import com.turbomanage.storm.entity.dao.ValueEntityDao;

public class FilterTestCase extends AndroidTestCase {

	private static final byte BYTE_VALUE = (byte) 13;
	private static final byte[] BLOB_VALUE = "blobField".getBytes();
	private static final boolean BOOLEAN_VALUE = true;
	private static final char CHAR_VALUE = 'q';
	private static final double DOUBLE_VALUE = Math.PI;
	private static final EnumType ENUM_VALUE = EnumType.VALUE2;
	private static final float FLOAT_VALUE = (float) Math.PI;
	private static final int INT_VALUE = 121393;
	private static final long LONG_VALUE = 2971215073L;
	private static final int SHORT_VALUE = 21;
	private Context ctx;
	private SimpleEntityDao dao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ctx = getContext();
		dao = new SimpleEntityDao(ctx);
		insertTestEntity();
	}

	@Override
	protected void tearDown() throws Exception {
		dao.deleteAll();
		super.tearDown();
	}

	private long insertTestEntity() {
		SimpleEntity e = new SimpleEntity();
		e.setBlobField(BLOB_VALUE);
		e.setBooleanField(BOOLEAN_VALUE);
		e.setByteField(BYTE_VALUE);
		e.setCharField(CHAR_VALUE);
		e.setDoubleField(DOUBLE_VALUE);
		e.setEnumField(ENUM_VALUE);
		e.setFloatField((float) FLOAT_VALUE);
		e.setIntField(INT_VALUE);
		e.setLongField(LONG_VALUE);
		e.setShortField((short) SHORT_VALUE);
		return dao.insert(e);
	}

	public void testBuildOrderBy() {
		// Verify that ASC and DESC methods exist
		assertEquals("BLOBFIELD ASC", SimpleEntityTable.Columns.BLOBFIELD.asc());
		assertEquals("BLOBFIELD DESC", SimpleEntityTable.Columns.BLOBFIELD.desc());
		SimpleEntityDao dao = new SimpleEntityDao(ctx);
		try {
			dao.load().order();
			fail("order() with no arguments should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// test passes
		}
		Query<SimpleEntity> fb = dao.load().order(Columns.BLOBFIELD.asc());
		assertEquals("BLOBFIELD ASC", fb.orderBy);
		fb = dao.load().order(Columns.BLOBFIELD.asc(), Columns.DOUBLEFIELD.desc());
		assertEquals("BLOBFIELD ASC, DOUBLEFIELD DESC", fb.orderBy);
	}
	
	public void testBuildWhere() {
		// Verify that no predicates returns null
	}
	
	public void testOrder() {
		ValueEntity val1 = new ValueEntity(21);
		ValueEntity val2 = new ValueEntity(34);
		ValueEntity val3 = new ValueEntity(13);
		ValueEntity val4 = new ValueEntity(5);
		ValueEntity val5 = new ValueEntity(8);
		ValueEntityDao valueDao = new ValueEntityDao(ctx);
		valueDao.insert(val1);
		valueDao.insert(val2);
		valueDao.insert(val3);
		valueDao.insert(val4);
		valueDao.insert(val5);
		List<ValueEntity> sortedValues = valueDao.load().order(com.turbomanage.storm.entity.dao.ValueEntityTable.Columns.INTVALUE.desc()).list();
		assertTrue(sortedValues.size() == 5);
		int previous = sortedValues.get(0).getIntValue();
		for (int i = 1; i < sortedValues.size(); i++) {
			int val = sortedValues.get(i).getIntValue();
			assertTrue (val <= previous);
		}
	}
	
	public void testFilterByExample() {
		SimpleEntity exampleObj = new SimpleEntity();
		exampleObj.setEnumField(ENUM_VALUE);
		SimpleEntity result = dao.load().byExample(exampleObj).get();
		assertEquals(ENUM_VALUE, result.getEnumField());
	}
	
	public void testQueryByBlob() {
		try {
			dao.load().eq(Columns.BLOBFIELD, BLOB_VALUE);
			fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	public void testQueryByBoolean() {
		SimpleEntity result = dao.load().eq(Columns.BOOLEANFIELD, BOOLEAN_VALUE).get();
		assertEquals(BOOLEAN_VALUE, result.isBooleanField());
	}

	public void testQueryByByte() {
		SimpleEntity result = dao.load().eq(Columns.BYTEFIELD, BYTE_VALUE).get();
		assertEquals(BYTE_VALUE, result.getByteField());
	}

	public void testQueryByChar() {
		SimpleEntity result = dao.load().eq(Columns.CHARFIELD, CHAR_VALUE).get();
		assertEquals(CHAR_VALUE, result.getCharField());
	}

	public void testQueryByDouble() {
		try {
			dao.load().eq(Columns.DOUBLEFIELD, Math.PI);
			fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	public void testQueryByEnum() {
		SimpleEntity result = dao.load().eq(Columns.ENUMFIELD, ENUM_VALUE).get();
		assertEquals(ENUM_VALUE, result.getEnumField());
	}

	public void testQueryByFloat() {
		try {
			dao.load().eq(Columns.FLOATFIELD, FLOAT_VALUE);
			fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	public void testQueryByInt() {
		SimpleEntity result = dao.load().eq(Columns.INTFIELD, INT_VALUE).get();
		assertEquals(INT_VALUE, result.getIntField());
	}

	public void testQueryByLong() {
		SimpleEntity result = dao.load().eq(Columns.LONGFIELD, LONG_VALUE).get();
		assertEquals(LONG_VALUE, result.getLongField());
	}

	public void testQueryByShort() {
		SimpleEntity result = dao.load().eq(Columns.SHORTFIELD, (short) SHORT_VALUE).get();
		assertEquals(SHORT_VALUE, result.getShortField());
	}
	
}
