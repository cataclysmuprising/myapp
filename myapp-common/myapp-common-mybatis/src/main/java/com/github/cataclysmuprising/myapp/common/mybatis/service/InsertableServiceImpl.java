/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-common-mybatis - InsertableServiceImpl.java
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
import static com.github.cataclysmuprising.myapp.common.util.ObjectUtil.getObjectName;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.InsertableRepository;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.InsertableService;

public class InsertableServiceImpl<T extends BaseBean> implements InsertableService<T> {
    private static final Logger serviceLogger = LogManager.getLogger("serviceLogs." + InsertableServiceImpl.class.getName());
    private InsertableRepository<T> repository;

    public InsertableServiceImpl(InsertableRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long insert(T record, long recordRegId) throws DuplicatedEntryException, BusinessException {
        final String objectName = getObjectName(record);
        serviceLogger.info(LOG_PREFIX + "This transaction was initiated by User ID # {}" + LOG_SUFFIX, recordRegId);
        serviceLogger.info(LOG_PREFIX + "Transaction start for inserting {} informations." + LOG_SUFFIX, objectName);
        long lastInsertedId;
        try {
            lastInsertedId = repository.insert(record, recordRegId);
        } catch (DAOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
        serviceLogger.info(LOG_PREFIX + "Transaction finished successfully for inserting {} informations." + LOG_SUFFIX, objectName);
        return lastInsertedId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(List<T> records, long recordRegId) throws DuplicatedEntryException, BusinessException {
        final String objectName = getObjectName(records);
        serviceLogger.info(LOG_PREFIX + "This transaction was initiated by User ID # {}" + LOG_SUFFIX, recordRegId);
        serviceLogger.info(LOG_PREFIX + "Transaction start for inserting multi {} informations." + LOG_SUFFIX, objectName);
        try {
            repository.insert(records, recordRegId);
        } catch (DAOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
        serviceLogger.info(LOG_PREFIX + "Transaction finished successfully for inserting multi {} informations." + LOG_SUFFIX, objectName);
    }
}
