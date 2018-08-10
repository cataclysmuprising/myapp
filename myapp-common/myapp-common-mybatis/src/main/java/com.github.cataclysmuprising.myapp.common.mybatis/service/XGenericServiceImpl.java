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
 *  	myapp-common-mybatis - XGenericServiceImpl.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/10/18 3:06 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */
package com.github.cataclysmuprising.myapp.common.mybatis.service;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.ConsistencyViolationException;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.XGenericRepository;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.XGenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_PREFIX;
import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_SUFFIX;
import static com.github.cataclysmuprising.myapp.common.util.ObjectUtil.getObjectName;

public class XGenericServiceImpl<T extends BaseBean, C extends CommonCriteria> implements XGenericService<T, C> {
	private static final Logger serviceLogger = LogManager.getLogger("serviceLogs." + XGenericServiceImpl.class.getName());
	private XGenericRepository<T, C> dao;

	public XGenericServiceImpl(XGenericRepository<T, C> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insert(T record, long recordRegId) throws DuplicatedEntryException, BusinessException {
		final String objectName = getObjectName(record);
		serviceLogger.info(LOG_PREFIX + "This transaction was initiated by User ID # {}" + LOG_SUFFIX, recordRegId);
		serviceLogger.info(LOG_PREFIX + "Transaction start for inserting {} informations." + LOG_SUFFIX, objectName);
		try {
			dao.insert(record, recordRegId);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		serviceLogger.info(LOG_PREFIX + "Transaction finished successfully." + LOG_SUFFIX);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insert(List<T> records, long recordRegId) throws DuplicatedEntryException, BusinessException {
		final String objectName = getObjectName(records);
		serviceLogger.info(LOG_PREFIX + "This transaction was initiated by User ID # {}" + LOG_SUFFIX, recordRegId);
		serviceLogger.info(LOG_PREFIX + "Transaction start for inserting {} informations." + LOG_SUFFIX, objectName);
		try {
			dao.insert(records, recordRegId);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		serviceLogger.info(LOG_PREFIX + "Transaction finished successfully." + LOG_SUFFIX);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insert(long key1, long key2, long recordRegId) throws DuplicatedEntryException, BusinessException {
		serviceLogger.info(LOG_PREFIX + "This transaction was initiated by User ID # {}" + LOG_SUFFIX, recordRegId);
		serviceLogger.info(LOG_PREFIX + "Transaction start for inserting with keys [key1={},key2={}]" + LOG_SUFFIX, key1, key2);
		try {
			dao.insert(key1, key2, recordRegId);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		serviceLogger.info(LOG_PREFIX + "Transaction finished successfully." + LOG_SUFFIX);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public long delete(long key1, long key2, long recordUpdId) throws ConsistencyViolationException, BusinessException {
		serviceLogger.info(LOG_PREFIX + "This transaction was initiated by User ID # {}" + LOG_SUFFIX, recordUpdId);
		serviceLogger.info(LOG_PREFIX + "Transaction start for delete by Keys ==> [key1={},key2={}]" + LOG_SUFFIX, key1, key2);
		long totalEffectedRows;
		try {
			totalEffectedRows = dao.delete(key1, key2, recordUpdId);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		serviceLogger.info(LOG_PREFIX + "Transaction finished successfully.");
		return totalEffectedRows;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public long delete(C criteria, long recordUpdId) throws ConsistencyViolationException, BusinessException {
		serviceLogger.info(LOG_PREFIX + "This transaction was initiated by User ID # {}" + LOG_SUFFIX, recordUpdId);
		serviceLogger.info(LOG_PREFIX + "Transaction start for delete by criteria ==> {}" + LOG_SUFFIX, criteria);
		long totalEffectedRows;
		try {
			totalEffectedRows = dao.delete(criteria, recordUpdId);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		serviceLogger.info(LOG_PREFIX + "Transaction finished successfully." + LOG_SUFFIX);
		return totalEffectedRows;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<Long> selectRelatedKeysByKey1(long key1) throws BusinessException {
		serviceLogger.info(LOG_PREFIX + "Transaction start for fetching related keys by key1 # {}" + LOG_SUFFIX, key1);
		Set<Long> relatedKeys;
		try {
			relatedKeys = dao.selectByKey1(key1);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		serviceLogger.info(LOG_PREFIX + "Transaction finished successfully." + LOG_SUFFIX);
		return relatedKeys;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<Long> selectRelatedKeysByKey2(long key2) throws BusinessException {
		serviceLogger.info(LOG_PREFIX + "Transaction start for fetching related keys by key2 # {}" + LOG_SUFFIX, key2);
		Set<Long> relatedKeys;
		try {
			relatedKeys = dao.selectByKey2(key2);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		serviceLogger.info(LOG_PREFIX + "Transaction finished successfully." + LOG_SUFFIX);
		return relatedKeys;
	}

	@Override
	@Transactional(readOnly = true)
	public T select(long key1, long key2) throws BusinessException {
		serviceLogger.info(LOG_PREFIX + "Transaction start for fetching single record by Keys ==> [key1={},key2={}]" + LOG_SUFFIX, key1, key2);
		T record;
		try {
			record = dao.select(key1, key2);
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
			records = dao.selectList(criteria);
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
			count = dao.selectCounts(criteria);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		serviceLogger.info(LOG_PREFIX + "Transaction finished successfully." + LOG_SUFFIX);
		return count;
	}
}
