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
 *  	myapp-persistence - StaticContentUnitTest.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 3:31 PM
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
import com.github.cataclysmuprising.myapp.domain.bean.StaticContentBean;
import com.github.cataclysmuprising.myapp.domain.criteria.StaticContentCriteria;
import com.github.cataclysmuprising.myapp.persistence.repository.StaticContentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StaticContentUnitTest extends BaseUnitTest {

	private Logger testLogger = LogManager.getLogger("testLogs." + this.getClass());

	@Autowired
	private StaticContentRepository repository;

	@Test(groups = {"fetch"})
	public void testSelectAll() throws Exception {
		StaticContentCriteria criteria = new StaticContentCriteria();
		List<StaticContentBean> results = repository.selectList(criteria);
		showEntriesOfCollection(results);
	}

	@Test(groups = {"fetch"})
	public void testSelectByPrimaryKey() throws Exception {
		StaticContentBean result = repository.select(1L);
		testLogger.info("Result ==> " + result);
	}

	@Test(groups = {"fetch"})
	public void testSelectAllCount() throws Exception {
		StaticContentCriteria criteria = new StaticContentCriteria();
		long count = repository.selectCounts(criteria);
		testLogger.info("Total counts ==> " + count);
	}

	@Test(groups = {"fetch"})
	public void testSelectByCriteria() throws Exception {
		StaticContentCriteria criteria = new StaticContentCriteria();
		criteria.setIncludeIds(Arrays.asList(1L, 2L, 3L));
		criteria.setExcludeIds(Arrays.asList(7L, 8L, 9L));

		criteria.setFileName("Sample File");
		criteria.setFilePath("D:\\temp\\user.jpg");
		criteria.setFileSize("10KB");
		criteria.setFileType(StaticContentBean.FileType.IMAGE);
		StaticContentBean result = repository.select(criteria);
		testLogger.info("Result ==> " + result);
	}

	@Test(groups = {"insert"})
	public void insertSingle() throws DAOException, DuplicatedEntryException {
		StaticContentBean record = new StaticContentBean();
		record.setFileName("Sample Image");
		record.setFilePath("D:\\temp\\logo.jpg");
		record.setFileSize("10KB");
		record.setFileType(StaticContentBean.FileType.IMAGE);
		long lastInsertedRecordId = repository.insert(record, TEST_CREATE_USER_ID);
		testLogger.info("Last inserted ID ==> " + lastInsertedRecordId);
	}

	@Test(groups = {"insert"})
	public void insertMulti() throws DAOException, DuplicatedEntryException {

		List<StaticContentBean> records = new ArrayList<>();

		StaticContentBean record1 = new StaticContentBean();
		record1.setFileName("Banner Logo");
		record1.setFilePath("D:\\temp\\banner.jpg");
		record1.setFileSize("10KB");
		record1.setFileType(StaticContentBean.FileType.IMAGE);
		records.add(record1);

		StaticContentBean record2 = new StaticContentBean();
		record2.setFileName("Footer Logo");
		record2.setFilePath("D:\\temp\\footer.jpg");
		record2.setFileSize("10KB");
		record2.setFileType(StaticContentBean.FileType.IMAGE);
		records.add(record2);

		repository.insert(records, TEST_CREATE_USER_ID);
	}

	@Test(groups = {"update"})
	public void testSingleRecordUpdate() throws Exception {
		StaticContentBean record = new StaticContentBean();
		record.setId(1L);
		record.setFileName("Update Image");
		record.setFilePath("D:\\temp\\uploaded_image.jpg");
		record.setFileSize("10KB");
		record.setFileType(StaticContentBean.FileType.IMAGE);
		long totalEffectedRows = repository.update(record, TEST_UPDATE_USER_ID);
		testLogger.info("Total effected rows = " + totalEffectedRows);
	}

	@Test(groups = {"update"})
	public void testUpdateByCriteria() throws Exception {
		StaticContentCriteria criteria = new StaticContentCriteria();
		criteria.setId(3L);
		criteria.setIncludeIds(Arrays.asList(3L, 5L));
		criteria.setExcludeIds(Arrays.asList(1L, 2L));

		HashMap<String, Object> updateItems = new HashMap<>();
		updateItems.put("fileName", "Updated_profile_picture.png");
		repository.update(criteria, updateItems, TEST_UPDATE_USER_ID);
	}

	@Test(groups = {"delete"})
	public void testDeleteByPrimaryKey() throws Exception {
		long totalEffectedRows = repository.delete(3, TEST_UPDATE_USER_ID);
		testLogger.info("Total effected rows = " + totalEffectedRows);
	}

	@Test(groups = {"delete"})
	public void testDeleteByCriteria() throws Exception {
		StaticContentCriteria criteria = new StaticContentCriteria();
		criteria.setIncludeIds(Arrays.asList(1L, 2L, 3L));
		criteria.setExcludeIds(Arrays.asList(7L, 8L, 9L));

		criteria.setFileName("user_logo.png");
		long totalEffectedRows = repository.delete(criteria, TEST_UPDATE_USER_ID);
		testLogger.info("Total effected rows = " + totalEffectedRows);
	}
}
