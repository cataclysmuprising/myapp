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
 *  	myapp-persistence - LoginHistoryUnitTest.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 3:33 PM
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
import com.github.cataclysmuprising.myapp.domain.bean.LoginHistoryBean;
import com.github.cataclysmuprising.myapp.domain.criteria.LoginHistoryCriteria;
import com.github.cataclysmuprising.myapp.persistence.repository.LoginHistoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginHistoryUnitTest extends BaseUnitTest {

	private Logger testLogger = LogManager.getLogger("testLogs." + this.getClass());

	@Autowired
	private LoginHistoryRepository repository;

	@Test(groups = {"fetch"})
	public void testSelectAll() throws Exception {
		LoginHistoryCriteria criteria = new LoginHistoryCriteria();
		List<LoginHistoryBean> results = repository.selectList(criteria);
		showEntriesOfCollection(results);
	}

	@Test(groups = {"fetch"})
	public void testSelectByPrimaryKey() throws Exception {
		LoginHistoryBean result = repository.select(1L);
		testLogger.info("Result ==> " + result);
	}

	@Test(groups = {"fetch"})
	public void testSelectAllCount() throws Exception {
		LoginHistoryCriteria criteria = new LoginHistoryCriteria();
		long count = repository.selectCounts(criteria);
		testLogger.info("Total counts ==> " + count);
	}

	@Test(groups = {"fetch"})
	public void testSelectByCriteria() throws Exception {
		LoginHistoryCriteria criteria = new LoginHistoryCriteria();
		criteria.setIncludeIds(Arrays.asList(1L, 2L, 3L));
		criteria.setExcludeIds(Arrays.asList(7L, 8L, 9L));

		criteria.setUserId(1L);
		criteria.setWithUser(true);
		LoginHistoryBean result = repository.select(criteria);
		testLogger.info("Result ==> " + result);
	}

	@Test(groups = {"insert"})
	public void insertSingle() throws DAOException, DuplicatedEntryException {
		LoginHistoryBean record = new LoginHistoryBean();
		record.setUserId(1L);
		record.setIpAddress("127.0.1");
		record.setOs("Microsoft Window");
		record.setUserAgent("Mozilla Firefox");
		record.setLoginDate(DateTime.now());
		long lastInsertedRecordId = repository.insert(record, TEST_CREATE_USER_ID);
		testLogger.info("Last inserted ID ==> " + lastInsertedRecordId);
	}

	@Test(groups = {"insert"})
	public void insertMulti() throws DAOException, DuplicatedEntryException {

		List<LoginHistoryBean> records = new ArrayList<>();

		LoginHistoryBean record1 = new LoginHistoryBean();
		record1.setUserId(1L);
		record1.setIpAddress("192.168.0.1");
		record1.setOs("Linux Ubuntu");
		record1.setUserAgent("Opera");
		record1.setLoginDate(DateTime.now());
		records.add(record1);

		LoginHistoryBean record2 = new LoginHistoryBean();
		record2.setUserId(2L);
		record2.setIpAddress("192.168.0.2");
		record2.setOs("Mac OSX");
		record2.setUserAgent("Google Chrome");
		record2.setLoginDate(DateTime.now());
		records.add(record2);

		repository.insert(records, TEST_CREATE_USER_ID);
	}

	@Test(groups = {"delete"})
	public void testDeleteByPrimaryKey() throws Exception {
		long totalEffectedRows = repository.delete(3, TEST_UPDATE_USER_ID);
		testLogger.info("Total effected rows = " + totalEffectedRows);
	}

	@Test(groups = {"delete"})
	public void testDeleteByCriteria() throws Exception {
		LoginHistoryCriteria criteria = new LoginHistoryCriteria();
		criteria.setIncludeIds(Arrays.asList(1L, 2L, 3L));
		criteria.setExcludeIds(Arrays.asList(7L, 8L, 9L));

		criteria.setUserId(1L);
		long totalEffectedRows = repository.delete(criteria, TEST_UPDATE_USER_ID);
		testLogger.info("Total effected rows = " + totalEffectedRows);
	}
}
