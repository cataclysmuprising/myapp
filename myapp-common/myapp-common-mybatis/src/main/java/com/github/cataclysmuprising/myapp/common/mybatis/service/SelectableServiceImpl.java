/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-common-mybatis - SelectableServiceImpl.java
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
package com.github.cataclysmuprising.myapp.common.mybatis.service;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_PREFIX;
import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_SUFFIX;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.SelectableRepository;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.SelectableService;

public class SelectableServiceImpl<T extends BaseBean, C extends CommonCriteria> implements SelectableService<T, C> {
    private static final Logger serviceLogger = LogManager.getLogger("serviceLogs." + SelectableServiceImpl.class.getName());
    private SelectableRepository<T, C> repository;

    public SelectableServiceImpl(SelectableRepository<T, C> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public T select(long primaryKey) throws BusinessException {
        serviceLogger.info(LOG_PREFIX + "Transaction start for fetch by primary key # {} ==> " + LOG_SUFFIX, primaryKey);
        T record;
        try {
            record = repository.select(primaryKey);
        } catch (DAOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
        serviceLogger.info(LOG_PREFIX + "Transaction finished successfully." + LOG_SUFFIX);
        return record;
    }

    @Override
    @Transactional(readOnly = true)
    public T select(C criteria) throws BusinessException {
        serviceLogger.info(LOG_PREFIX + "Transaction start for fetching single record by criteria ==> {}" + LOG_SUFFIX, criteria);
        T record;
        try {
            record = repository.select(criteria);
        } catch (DAOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
        serviceLogger.info(LOG_PREFIX + "Transaction finished successfully." + LOG_SUFFIX);
        return record;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> selectList(C criteria) throws BusinessException {
        serviceLogger.info(LOG_PREFIX + "Transaction start for fetching multi records by criteria ==> {}" + LOG_SUFFIX, criteria);
        List<T> records;
        try {
            records = repository.selectList(criteria);
        } catch (DAOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
        serviceLogger.info(LOG_PREFIX + "Transaction finished successfully." + LOG_SUFFIX);
        return records;
    }

    @Override
    @Transactional(readOnly = true)
    public long selectCounts(C criteria) throws BusinessException {
        serviceLogger.info(LOG_PREFIX + "Transaction start for fetching record counts by criteria ==> {}" + LOG_SUFFIX, criteria);
        long count;
        try {
            count = repository.selectCounts(criteria);
        } catch (DAOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
        serviceLogger.info(LOG_PREFIX + "Transaction finished successfully." + LOG_SUFFIX);
        return count;
    }
}
