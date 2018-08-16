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
 *  	myapp-persistence - ActionUnitTest.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 1:24 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

package com.github.cataclysmuprisingp.myapp.unitTest;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.domain.bean.ActionBean;
import com.github.cataclysmuprising.myapp.domain.criteria.ActionCriteria;
import com.github.cataclysmuprising.myapp.persistence.repository.ActionRepository;

public class ActionUnitTest extends BaseUnitTest {

	private Logger testLogger = LogManager.getLogger("testLogs." + this.getClass());

	@Autowired
	private ActionRepository repository;

	@Test(groups = { "fetch" })
	public void testSelectAll() throws Exception {
		ActionCriteria criteria = new ActionCriteria();
		List<ActionBean> results = repository.selectList(null);
		showEntriesOfCollection(results);
	}

	@Test(groups = { "fetch" })
	public void testSelectByPrimaryKey() throws Exception {
		ActionBean result = repository.select(1L);
		testLogger.info("Result ==> " + result);
	}

	@Test(groups = { "fetch" })
	public void testSelectAllCount() throws Exception {
		ActionCriteria criteria = new ActionCriteria();
		long count = repository.selectCounts(criteria);
		testLogger.info("Total counts ==> " + count);
	}

	@Test(groups = { "fetch" })
	public void testSelectCountByCriteria() throws Exception {
		ActionCriteria criteria = new ActionCriteria();
		criteria.setId(1001L);
		criteria.setIncludeIds(Arrays.asList(1L, 2L, 3L));
		criteria.setExcludeIds(Arrays.asList(7L, 8L, 9L));
		long count = repository.selectCounts(criteria);
		testLogger.info("Total counts ==> " + count);
	}

	@Test(groups = { "fetch" })
	public void testSelectByCriteria() throws Exception {
		ActionCriteria criteria = new ActionCriteria();
		criteria.setId(1L);
		criteria.setIncludeIds(Arrays.asList(1L, 2L, 3L));
		criteria.setExcludeIds(Arrays.asList(7L, 8L, 9L));
		criteria.setOffset(0L);
		criteria.setLimit(10);
		criteria.setWord("");
		criteria.setOrderBy("id");
		criteria.setOrder(CommonCriteria.Order.ASC);
		List<ActionBean> results = repository.selectList(criteria);
		showEntriesOfCollection(results);
	}

	@Test(groups = { "fetch" })
	public void testSelectAvailableActionsForAuthenticatedUser() throws Exception {
		List<String> results = repository.selectAvailableActionsForAuthenticatedUser("Dashboard", Arrays.asList(1L));
		showEntriesOfCollection(results);
	}
}
