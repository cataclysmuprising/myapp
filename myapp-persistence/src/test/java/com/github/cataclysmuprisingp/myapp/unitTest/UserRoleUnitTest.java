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
 *  	myapp-persistence - UserRoleUnitTest.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 3:02 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

/*
 * @author Mg Than Htike Aung {@literal <rage.cataclysm@gmail.com@address>}
 * @Since 1.0
 *
 */
package com.github.cataclysmuprisingp.myapp.unitTest;

import com.github.cataclysmuprising.myapp.domain.bean.UserRoleBean;
import com.github.cataclysmuprising.myapp.domain.criteria.UserRoleCriteria;
import com.github.cataclysmuprising.myapp.persistence.repository.UserRoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class UserRoleUnitTest extends BaseUnitTest {

	private Logger testLogger = LogManager.getLogger("testLogs." + this.getClass());

	@Autowired
	private UserRoleRepository repository;

	@Test(groups = {"fetch"})
	public void testSelectAll() throws Exception {
		UserRoleCriteria criteria = new UserRoleCriteria();
		List<UserRoleBean> results = repository.selectList(criteria);
		showEntriesOfCollection(results);
	}

	@Test(groups = {"fetch"})
	public void testSelectAllCount() throws Exception {
		UserRoleCriteria criteria = new UserRoleCriteria();
		long count = repository.selectCounts(criteria);
		testLogger.info("Total counts ==> " + count);
	}

	@Test(groups = {"fetch"})
	public void testSelectByKey1() throws Exception {
		Set<Long> relatedIds = repository.selectByKey1(1L);
		showEntriesOfCollection(relatedIds);
	}

	@Test(groups = {"fetch"})
	public void testSelectByKey2() throws Exception {
		Set<Long> relatedIds = repository.selectByKey2(2001L);
		showEntriesOfCollection(relatedIds);
	}

	@Test(groups = {"fetch"})
	public void testSelectByKeys() throws Exception {
		UserRoleBean result = repository.select(1L, 2001L);
		testLogger.info("Result ==> " + result);
	}

	@Test(groups = {"fetch"})
	public void testSelectByCriteria() throws Exception {
		UserRoleCriteria criteria = new UserRoleCriteria();
		criteria.setUserId(1L);
		criteria.setRoleId(1L);
		criteria.setWithUser(true);
		criteria.setWithRole(true);
		List<UserRoleBean> results = repository.selectList(criteria);
		showEntriesOfCollection(results);
	}

	@Test(groups = {"insert"})
	public void testInsertSingle() throws Exception {
		UserRoleBean record = new UserRoleBean(1001L, 1001L);
		repository.insert(record, TEST_CREATE_USER_ID);
	}

	@Test(groups = {"insert"})
	public void testInsertSingleByKeys() throws Exception {
		repository.insert(1001L, 1001L, TEST_CREATE_USER_ID);
	}

	@Test(groups = {"delete"})
	public void testDeleteByKeys() throws Exception {
		long totalEffectedRows = repository.delete(1L, 2001L, TEST_UPDATE_USER_ID);
		testLogger.info("Total effected rows ==> " + totalEffectedRows);
	}
}
