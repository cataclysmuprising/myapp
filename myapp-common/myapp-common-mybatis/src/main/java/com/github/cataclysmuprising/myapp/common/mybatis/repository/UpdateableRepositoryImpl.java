/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-common-mybatis - UpdateableRepositoryImpl.java
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
package com.github.cataclysmuprising.myapp.common.mybatis.repository;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.DUPLICATE_KEY_UPDATE_FAILED_MSG;
import static com.github.cataclysmuprising.myapp.common.util.ObjectUtil.getObjectName;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.Assert;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.mybatis.mapper.base.UpdateableMapper;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.UpdateableRepository;

public class UpdateableRepositoryImpl<T extends BaseBean, C extends CommonCriteria> implements UpdateableRepository<T, C> {
    private static final Logger logger = LogManager.getLogger("repositoryLogs." + UpdateableRepositoryImpl.class.getName());

    private UpdateableMapper<T, C> mapper;

    public UpdateableRepositoryImpl(UpdateableMapper<T, C> mapper) {
        this.mapper = mapper;
    }

    @Override
    public long update(T record, long recordUpdId) throws DuplicatedEntryException, DAOException {
        Assert.notNull(record, "Record shouldn't be Null.");
        final String objectName = getObjectName(record);
        long totalEffectedRows;
        logger.debug("[START] : >>> --- Updating single {} informations with Id # {} ---", objectName, record.getId());
        try {
            record.setRecordUpdId(recordUpdId);
            totalEffectedRows = mapper.update(record);
        } catch (DuplicateKeyException e) {
            String errorMsg = "xxx " + DUPLICATE_KEY_UPDATE_FAILED_MSG + " xxx";
            throw new DuplicatedEntryException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "xxx Error occured while updating " + objectName + " data ==> " + record + " xxx";
            throw new DAOException(errorMsg, e);
        }
        logger.debug("[FINISH] : <<< --- Updating single {} informations with Id ---", objectName);
        return totalEffectedRows;
    }

    @Override
    public void update(List<T> records, long recordUpdId) throws DuplicatedEntryException, DAOException {
        Assert.notNull(records, "Records shouldn't be Null.");
        Assert.notEmpty(records, "Records shouldn't be Empty.");
        final String objectName = getObjectName(records.get(0));
        logger.debug("[START] : >>> --- Updating multi {} informations ---", objectName);
        for (T record : records) {
            try {
                record.setRecordUpdId(recordUpdId);
                mapper.update(record);
            } catch (DuplicateKeyException e) {
                String errorMsg = "xxx " + DUPLICATE_KEY_UPDATE_FAILED_MSG + " xxx";
                throw new DuplicatedEntryException(errorMsg, e);
            } catch (Exception e) {
                String errorMsg = "xxx Error occured while updating " + objectName + " data ==> " + record + " xxx";
                throw new DAOException(errorMsg, e);
            }
        }
        logger.debug("[FINISH] : <<< --- Updating multi {} informations ---", objectName);
    }

    @Override
    public long update(C criteria, HashMap<String, Object> updateItems, long recordUpdId) throws DAOException, DuplicatedEntryException {
        Assert.notNull(criteria, "Criteria shouldn't be Null.");
        Assert.notEmpty(updateItems, "UpdateItems shouldn't be Empty.");
        long totalEffectedRows;
        final String objectName = getObjectName(criteria.getObjectClass());
        logger.debug("[START] : >>> --- Updating multi {} informations with criteria ==> {} ---", objectName, criteria);
        try {
            updateItems.put("recordUpdId", recordUpdId);
            totalEffectedRows = mapper.updateWithCriteria(criteria, updateItems);
        } catch (DuplicateKeyException e) {
            String errorMsg = "xxx " + DUPLICATE_KEY_UPDATE_FAILED_MSG + " xxx";
            throw new DuplicatedEntryException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "xxx Error occured while updating multiple " + objectName + " informations [Values] ==> " + updateItems + " with [Criteria] ==> " + criteria + " xxx";
            throw new DAOException(errorMsg, e);
        }
        logger.debug("[FINISH] : <<< --- Updating multi {} informations with criteria ---", objectName);
        return totalEffectedRows;
    }
}
