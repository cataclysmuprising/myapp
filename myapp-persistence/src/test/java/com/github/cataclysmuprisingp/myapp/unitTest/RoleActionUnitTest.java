/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-persistence - RoleActionUnitTest.java
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.github.cataclysmuprisingp.myapp.unitTest;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.github.cataclysmuprising.myapp.domain.bean.RoleActionBean;
import com.github.cataclysmuprising.myapp.domain.criteria.RoleActionCriteria;
import com.github.cataclysmuprising.myapp.persistence.repository.RoleActionRepository;

public class RoleActionUnitTest extends BaseUnitTest {

    private Logger testLogger = LogManager.getLogger("testLogs." + this.getClass());

    @Autowired
    private RoleActionRepository repository;

    @Test(groups = { "fetch" })
    public void testSelectAll() throws Exception {
        RoleActionCriteria criteria = new RoleActionCriteria();
        List<RoleActionBean> results = repository.selectList(criteria);
        showEntriesOfCollection(results);
    }

    @Test(groups = { "fetch" })
    public void testSelectAllCount() throws Exception {
        RoleActionCriteria criteria = new RoleActionCriteria();
        long count = repository.selectCounts(criteria);
        testLogger.info("Total counts ==> " + count);
    }

    @Test(groups = { "fetch" })
    public void testSelectByKey1() throws Exception {
        Set<Long> relatedIds = repository.selectByKey1(1L);
        showEntriesOfCollection(relatedIds);
    }

    @Test(groups = { "fetch" })
    public void testSelectByKey2() throws Exception {
        Set<Long> relatedIds = repository.selectByKey2(2001L);
        showEntriesOfCollection(relatedIds);
    }

    @Test(groups = { "fetch" })
    public void testSelectByKeys() throws Exception {
        RoleActionBean result = repository.select(1L, 2001L);
        testLogger.info("Result ==> " + result);
    }

    @Test(groups = { "fetch" })
    public void testSelectByCriteria() throws Exception {
        RoleActionCriteria criteria = new RoleActionCriteria();
        criteria.setRoleId(1L);
        criteria.setActionId(2001L);
        criteria.setWithRole(true);
        criteria.setWithAction(true);
        List<RoleActionBean> results = repository.selectList(criteria);
        showEntriesOfCollection(results);
    }

    @Test(groups = { "insert" })
    public void testInsertSingle() throws Exception {
        RoleActionBean record = new RoleActionBean(1001L, 1001L);
        repository.insert(record, TEST_CREATE_USER_ID);
    }

    @Test(groups = { "insert" })
    public void testInsertSingleByKeys() throws Exception {
        repository.insert(1001L, 1001L, TEST_CREATE_USER_ID);
    }

    @Test(groups = { "delete" })
    public void testDeleteByKeys() throws Exception {
        long totalEffectedRows = repository.delete(1L, 2001L, TEST_UPDATE_USER_ID);
        testLogger.info("Total effected rows ==> " + totalEffectedRows);
    }
}
