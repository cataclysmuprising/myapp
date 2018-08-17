/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-persistence - UserUnitTest.java
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.domain.bean.AuthenticatedUserBean;
import com.github.cataclysmuprising.myapp.domain.bean.UserBean;
import com.github.cataclysmuprising.myapp.domain.criteria.UserCriteria;
import com.github.cataclysmuprising.myapp.persistence.repository.UserRepository;

public class UserUnitTest extends BaseUnitTest {

    private Logger testLogger = LogManager.getLogger("testLogs." + this.getClass());

    @Autowired
    private UserRepository repository;

    @Test(groups = { "fetch" })
    public void testSelectAll() throws Exception {
        UserCriteria criteria = new UserCriteria();
        List<UserBean> results = repository.selectList(criteria);
        showEntriesOfCollection(results);
    }

    @Test(groups = { "fetch" })
    public void testSelectByPrimaryKey() throws Exception {
        UserBean result = repository.select(1L);
        testLogger.info("Result ==> " + result);
    }

    @Test(groups = { "fetch" })
    public void testSelectAllCount() throws Exception {
        UserCriteria criteria = new UserCriteria();
        long count = repository.selectCounts(criteria);
        testLogger.info("Total counts ==> " + count);
    }

    @Test(groups = { "fetch" })
    public void testSelectByCriteria() throws Exception {
        UserCriteria criteria = new UserCriteria();
        criteria.setIncludeIds(Arrays.asList(1L, 2L, 3L));
        criteria.setExcludeIds(Arrays.asList(7L, 8L, 9L));
        criteria.setWithStaticContent(true);

        criteria.setName("Super User");
        criteria.setEmail("superuser@sample.com");
        criteria.setStatus(UserBean.Status.ACTIVE);
        criteria.setNrc("12/XYZ(N)998877");
        criteria.setPhone("09-111111");
        UserBean result = repository.select(criteria);
        testLogger.info("Result ==> " + result);
    }

    @Test(groups = { "fetch" })
    public void testSelectAuthenticatedUserInfo() throws Exception {
        AuthenticatedUserBean result = repository.selectAuthenticatedUser("superuser@sample.com");
        testLogger.info("Result ==> " + result);
    }

    @Test(groups = { "insert" })
    public void insertSingle() throws DAOException, DuplicatedEntryException {
        UserBean record = new UserBean();
        record.setContentId(1L);
        record.setName("Mg Mg");
        record.setEmail("mgmg@sample.com");
        record.setPassword("sample_password");
        record.setNrc("12/abc 12345");
        record.setPhone("1111111");
        record.setStatus(UserBean.Status.ACTIVE);
        record.setAge(20);
        record.setGender(UserBean.Gender.MALE);
        record.setDob(LocalDate.now());
        record.setAddress("Yangon/Myanmar");

        long lastInsertedRecordId = repository.insert(record, TEST_CREATE_USER_ID);
        testLogger.info("Last inserted ID ==> " + lastInsertedRecordId);
    }

    @Test(groups = { "insert" })
    public void insertMulti() throws DAOException, DuplicatedEntryException {

        List<UserBean> records = new ArrayList<>();

        UserBean record1 = new UserBean();
        record1.setContentId(2L);
        record1.setName("Kyaw Kyaw");
        record1.setEmail("kyawkyaw@sample.com");
        record1.setPassword("sample_password");
        record1.setNrc("12/abc 67890");
        record1.setPhone("22222222");
        record1.setStatus(UserBean.Status.ACTIVE);
        record1.setAge(26);
        record1.setGender(UserBean.Gender.MALE);
        record1.setDob(LocalDate.now());
        record1.setAddress("Mandalay/Myanmar");
        records.add(record1);

        UserBean record2 = new UserBean();
        record2.setContentId(3L);
        record2.setName("Hla Hla");
        record2.setEmail("hlahla@sample.com");
        record2.setPassword("sample_password");
        record2.setNrc("12/ghi 24680");
        record2.setPhone("333333333");
        record2.setStatus(UserBean.Status.ACTIVE);
        record2.setAge(25);
        record2.setGender(UserBean.Gender.FEMALE);
        record2.setDob(LocalDate.now());
        record2.setAddress("Taunggyi/Myanmar");
        records.add(record2);

        repository.insert(records, TEST_CREATE_USER_ID);
    }

    @Test(groups = { "update" })
    public void testSingleRecordUpdate() throws Exception {
        UserBean record = new UserBean();
        record.setId(3L);
        record.setName("Ma Ma");
        record.setPassword("mamaP@ssword");
        record.setNrc("12/000000");
        record.setPhone("09120130");
        record.setStatus(UserBean.Status.ACTIVE);
        record.setAge(22);
        record.setGender(UserBean.Gender.FEMALE);
        record.setDob(LocalDate.now());
        record.setAddress("PhyinOoLwin/Myanmar");

        long totalEffectedRows = repository.update(record, TEST_UPDATE_USER_ID);
        testLogger.info("Total effected rows = " + totalEffectedRows);
    }

    @Test(groups = { "update" })
    public void testUpdateByCriteria() throws Exception {
        UserCriteria criteria = new UserCriteria();
        criteria.setId(3L);
        criteria.setIncludeIds(Arrays.asList(3L, 5L));
        criteria.setExcludeIds(Arrays.asList(1L, 2L));

        HashMap<String, Object> updateItems = new HashMap<>();
        updateItems.put("status", 2);
        repository.update(criteria, updateItems, TEST_UPDATE_USER_ID);
    }

    @Test(groups = { "delete" })
    public void testDeleteByPrimaryKey() throws Exception {
        long totalEffectedRows = repository.delete(3, TEST_UPDATE_USER_ID);
        testLogger.info("Total effected rows = " + totalEffectedRows);
    }

    @Test(groups = { "delete" })
    public void testDeleteByCriteria() throws Exception {
        UserCriteria criteria = new UserCriteria();
        criteria.setIncludeIds(Arrays.asList(1L, 2L, 3L));
        criteria.setExcludeIds(Arrays.asList(7L, 8L, 9L));

        criteria.setName("Super User");
        criteria.setEmail("superuser@sample.com");
        criteria.setStatus(UserBean.Status.ACTIVE);
        criteria.setNrc("12/XYZ(N)998877");
        criteria.setPhone("09-111111");
        long totalEffectedRows = repository.delete(criteria, TEST_UPDATE_USER_ID);
        testLogger.info("Total effected rows = " + totalEffectedRows);
    }
}
