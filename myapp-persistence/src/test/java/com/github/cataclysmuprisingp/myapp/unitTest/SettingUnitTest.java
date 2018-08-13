/*
 *
 *   This source file is free software, available under the following license: MIT license.
 *   Copyright (c) 2018, Than Htike Aung(https://github.com/cataclysmuprising) All Rights Reserved.
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 *
 *  	myapp-persistence - SettingUnitTest.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 1:54 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

/*
 * @author Mg Than Htike Aung {@literal <rage.cataclysm@gmail.com@address>}
 * @Since 1.0
 *
 */
package com.github.cataclysmuprisingp.myapp.unitTest;

import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.domain.bean.SettingBean;
import com.github.cataclysmuprising.myapp.domain.criteria.SettingCriteria;
import com.github.cataclysmuprising.myapp.persistence.repository.SettingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SettingUnitTest extends BaseUnitTest {

	private Logger testLogger = LogManager.getLogger("testLogs." + this.getClass());

	@Autowired
	private SettingRepository repository;

	@Test(groups = {"fetch"})
	public void testSelectAll() throws Exception {
		SettingCriteria criteria = new SettingCriteria();
		List<SettingBean> results = repository.selectList(criteria);
		showEntriesOfCollection(results);
	}

	@Test(groups = {"fetch"})
	public void testSelectByPrimaryKey() throws Exception {
		SettingBean result = repository.select(1L);
		testLogger.info("Result ==> " + result);
	}

	@Test(groups = {"fetch"})
	public void testSelectAllCount() throws Exception {
		SettingCriteria criteria = new SettingCriteria();
		long count = repository.selectCounts(criteria);
		testLogger.info("Total counts ==> " + count);
	}

	@Test(groups = {"fetch"})
	public void testSelectByCriteria() throws Exception {
		SettingCriteria criteria = new SettingCriteria();
		criteria.setIncludeIds(Arrays.asList(1L, 2L, 3L));
		criteria.setExcludeIds(Arrays.asList(7L, 8L, 9L));

		criteria.setName("Sample Name");
		criteria.setValue("Sample Value");
		criteria.setType("Sample Type");
		criteria.setGroup("Sample Group");
		criteria.setSubGroup("Sample Sub Group");
		SettingBean result = repository.select(criteria);
		testLogger.info("Result ==> " + result);
	}

	@Test(groups = {"insert"})
	public void insertSingle() throws DAOException, DuplicatedEntryException {
		SettingBean record = new SettingBean();
		record.setName("Sample Name");
		record.setValue("Sample Value");
		record.setType("Sample Type");
		record.setGroup("Sample Group");
		record.setSubGroup("Sample Sub Group");
		long lastInsertedRecordId = repository.insert(record, TEST_CREATE_USER_ID);
		testLogger.info("Last inserted ID ==> " + lastInsertedRecordId);
	}

	@Test(groups = {"insert"})
	public void insertMulti() throws DAOException, DuplicatedEntryException {

		List<SettingBean> records = new ArrayList<>();

		SettingBean record1 = new SettingBean();
		record1.setName("Sample Name 1");
		record1.setValue("Sample Value 1");
		record1.setType("Sample Type 1");
		record1.setGroup("Sample Group 1");
		record1.setSubGroup("Sample Sub Group 1");
		records.add(record1);

		SettingBean record2 = new SettingBean();
		record2.setName("Sample Name 2");
		record2.setValue("Sample Value 2");
		record2.setType("Sample Type 2");
		record2.setGroup("Sample Group 2");
		record2.setSubGroup("Sample Sub Group 2");
		records.add(record2);

		repository.insert(records, TEST_CREATE_USER_ID);
	}

	@Test(groups = {"update"})
	public void testSingleRecordUpdate() throws Exception {
		SettingBean record = new SettingBean();
		record.setId(1L);
		record.setName("Updated Name");
		record.setValue("Updated Value");
		record.setType("Updated Type");
		record.setGroup("Updated Group");
		record.setSubGroup("Updated Sub Group");
		long totalEffectedRows = repository.update(record, TEST_UPDATE_USER_ID);
		testLogger.info("Total effected rows = " + totalEffectedRows);
	}

	@Test(groups = {"update"})
	public void testUpdateByCriteria() throws Exception {
		SettingCriteria criteria = new SettingCriteria();
		criteria.setId(1L);
		criteria.setIncludeIds(Arrays.asList(3L, 5L));
		criteria.setExcludeIds(Arrays.asList(1L, 2L));

		HashMap<String, Object> updateItems = new HashMap<>();
		updateItems.put("name", "Updated Name");
		repository.update(criteria, updateItems, TEST_UPDATE_USER_ID);
	}

	@Test(groups = {"delete"})
	public void testDeleteByPrimaryKey() throws Exception {
		long totalEffectedRows = repository.delete(1L, TEST_UPDATE_USER_ID);
		testLogger.info("Total effected rows = " + totalEffectedRows);
	}

	@Test(groups = {"delete"})
	public void testDeleteByCriteria() throws Exception {
		SettingCriteria criteria = new SettingCriteria();
		criteria.setIncludeIds(Arrays.asList(1L, 2L, 3L));
		criteria.setExcludeIds(Arrays.asList(7L, 8L, 9L));

		criteria.setName("Sample Name");
		long totalEffectedRows = repository.delete(criteria, TEST_UPDATE_USER_ID);
		testLogger.info("Total effected rows = " + totalEffectedRows);
	}
}
