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
 *  	myapp-common-mybatis - XGenericRepositoryImpl.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 2:30 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

package com.github.cataclysmuprising.myapp.common.mybatis.repository;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.exception.ConsistencyViolationException;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.mybatis.mapper.base.XGenericMapper;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.XGenericRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Set;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.DATA_INTEGRITY_VIOLATION_MSG;
import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.DUPLICATE_KEY_INSERT_FAILED_MSG;
import static com.github.cataclysmuprising.myapp.common.util.ObjectUtil.getObjectName;

public class XGenericRepositoryImpl<T extends BaseBean, C extends CommonCriteria> implements XGenericRepository<T, C> {
	private static final Logger logger = LogManager.getLogger("repositoryLogs." + XGenericRepositoryImpl.class.getName());

	private XGenericMapper<T, C> mapper;

	public XGenericRepositoryImpl(XGenericMapper<T, C> mapper) {
		this.mapper = mapper;
	}

	@Override
	public void insert(T record, long recordRegId) throws DuplicatedEntryException, DAOException {
		final String objectName = getObjectName(record);
		try {
			logger.debug("[START] : >>> --- Inserting single {} information ---", objectName);
			DateTime now = DateTime.now();
			record.setRecordRegDate(now);
			record.setRecordUpdDate(now);
			record.setRecordRegId(recordRegId);
			record.setRecordUpdId(recordRegId);
			mapper.insert(record);
		} catch (DuplicateKeyException e) {
			String errorMsg = "xxx " + DUPLICATE_KEY_INSERT_FAILED_MSG + " xxx";
			throw new DuplicatedEntryException(errorMsg, e);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while inserting " + objectName + " data ==> " + record + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Inserting single {} information ---", objectName);
	}

	@Override
	public void insert(List<T> records, long recordRegId) throws DuplicatedEntryException, DAOException {
		final String objectName = getObjectName(records);
		logger.debug("[START] : >>> --- Inserting multi {} informations ---", objectName);
		DateTime now = DateTime.now();
		for (T record : records) {
			record.setRecordRegDate(now);
			record.setRecordUpdDate(now);
			record.setRecordRegId(recordRegId);
			record.setRecordUpdId(recordRegId);
		}
		try {
			mapper.insertList(records);
		} catch (DuplicateKeyException e) {
			String errorMsg = "xxx " + DUPLICATE_KEY_INSERT_FAILED_MSG + ". xxx";
			throw new DuplicatedEntryException(errorMsg, e);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while inserting " + objectName + " datas ==> " + records + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Inserting multi {} informations ---", objectName);
	}

	@Override
	public void insert(long key1, long key2, long recordRegId) throws DuplicatedEntryException, DAOException {
		logger.debug("[START] : >>> --- Inserting single data information with ==> key1 = {} , key2 = {} ---", key1, key2);
		try {
			mapper.insertWithRelatedKeys(key1, key2, recordRegId);
		} catch (DuplicateKeyException e) {
			String errorMsg = "xxx " + DUPLICATE_KEY_INSERT_FAILED_MSG + " xxx";
			throw new DuplicatedEntryException(errorMsg, e);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while inserting single data ==> key1 = " + key1 + " , key2 = " + key2 + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Inserting single data information ---");
	}

	@Override
	public long delete(long key1, long key2, long recordUpdId) throws ConsistencyViolationException, DAOException {
		logger.debug("[START] : >>> --- Deleting single data information with ==> key1 " + key1 + " , key2 = " + key2 + " ---");
		long effectedRows;
		try {
			effectedRows = mapper.deleteByKeys(key1, key2);
		} catch (DataIntegrityViolationException e) {
			String errorMsg = "xxx " + DATA_INTEGRITY_VIOLATION_MSG + " xxx";
			throw new ConsistencyViolationException(errorMsg, e);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while deleting single data with ==> key1 = " + key1 + " , key2 = " + key2 + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Deleting single data information ---");
		return effectedRows;
	}

	@Override
	public long delete(C criteria, long recordUpdId) throws ConsistencyViolationException, DAOException {
		final String objectName = getObjectName(criteria.getObjectClass());
		logger.debug("[START] : >>> --- Deleting {} informations with criteria ==> {} ---", objectName, criteria);
		long effectedRows;
		try {
			effectedRows = mapper.deleteByCriteria(criteria);
		} catch (DataIntegrityViolationException e) {
			String errorMsg = "xxx " + DATA_INTEGRITY_VIOLATION_MSG + " xxx";
			throw new ConsistencyViolationException(errorMsg, e);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while deleting " + objectName + " data with criteria ==> " + criteria + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Deleting {} informations with criteria  ---", objectName);
		return effectedRows;
	}

	@Override
	public Set<Long> selectByKey1(long key1) throws DAOException {
		logger.debug("[START] : >>> --- Fetching  related Keys with key1 # {} ---", key1);
		Set<Long> relatedKeys;
		try {
			relatedKeys = mapper.selectRelatedKeysByKey1(key1);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while fetching related keys with key1 ==> " + key1 + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Fetching related related Keys with key1 ---");
		return relatedKeys;
	}

	@Override
	public Set<Long> selectByKey2(long key2) throws DAOException {
		logger.debug("[START] : >>> --- Fetching related Keys with key2 # {} ---", key2);
		Set<Long> relatedKeys;
		try {
			relatedKeys = mapper.selectRelatedKeysByKey2(key2);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while fetching related keys with key2 ==> " + key2 + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Fetching related Keys with key2 ---");
		return relatedKeys;
	}

	@Override
	public T select(long key1, long key2) throws DAOException {
		logger.debug("[START] : >>> --- Fetching single data information with both key1 and key2 ==> key1 = {} , key2 = {} ---", key1, key2);
		T record;
		try {
			record = mapper.selectByKeys(key1, key2);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while fetching single data information with ==> key1 = " + key1 + " , key2 = " + key2 + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Fetching single data information with both key1 and key2 ---");
		return record;
	}

	@Override
	public List<T> selectList(C criteria) throws DAOException {
		final String objectName = getObjectName(criteria.getObjectClass());
		logger.debug("[START] : >>> --- Fetching multi {} informations with criteria ---", objectName);
		List<T> results;
		try {
			results = mapper.selectMultiRecords(criteria);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while fetching multi " + objectName + " informations with criteria ==> " + criteria + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Fetching multi {} informations with criteria ---", objectName);
		return results;
	}

	@Override
	public long selectCounts(C criteria) throws DAOException {
		final String objectName = getObjectName(criteria.getObjectClass());
		logger.debug("[START] : >>> --- Fetching {} counts with criteria ==> {} ---", objectName, criteria);
		long count;
		try {
			count = mapper.selectCounts(criteria);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while counting " + objectName + " records with criteria ==> " + criteria + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Fetching {} counts with criteria ---", objectName);
		return count;
	}
}
