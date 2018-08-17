/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-common-mybatis - XGenericRepository.java
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
package com.github.cataclysmuprising.myapp.common.mybatis.repository.api;

import java.util.List;
import java.util.Set;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.exception.ConsistencyViolationException;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;

public interface XGenericRepository<T extends BaseBean, C extends CommonCriteria> {

    void insert(T record, long recordRegId) throws DuplicatedEntryException, DAOException;

    void insert(List<T> records, long recordRegId) throws DuplicatedEntryException, DAOException;

    void insert(long key1, long key2, long recordRegId) throws DuplicatedEntryException, DAOException;

    long delete(long key1, long key2, long recordUpdId) throws ConsistencyViolationException, DAOException;

    long delete(C criteria, long recordUpdId) throws ConsistencyViolationException, DAOException;

    Set<Long> selectByKey1(long key1) throws DAOException;

    Set<Long> selectByKey2(long key2) throws DAOException;

    T select(long key1, long key2) throws DAOException;

    List<T> selectList(C criteria) throws DAOException;

    long selectCounts(C criteria) throws DAOException;
}
