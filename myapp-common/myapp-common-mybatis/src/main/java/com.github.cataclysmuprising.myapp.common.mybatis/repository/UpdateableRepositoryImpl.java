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
 *  	myapp-common-mybatis - UpdateableRepositoryImpl.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/10/18 1:26 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

package com.github.cataclysmuprising.myapp.common.mybatis.repository;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.mybatis.mapper.base.UpdateableMapper;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.UpdateableRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;

import java.util.HashMap;
import java.util.List;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.DUPLICATE_KEY_UPDATE_FAILED_MSG;
import static com.github.cataclysmuprising.myapp.common.util.ObjectUtil.getObjectName;

public class UpdateableRepositoryImpl<T extends BaseBean, C extends CommonCriteria> implements UpdateableRepository<T, C> {
	private static final Logger logger = LogManager.getLogger("repositoryLogs." + UpdateableRepositoryImpl.class.getName());

	private UpdateableMapper<T, C> mapper;

	public UpdateableRepositoryImpl(UpdateableMapper<T, C> mapper) {
		this.mapper = mapper;
	}

	@Override
	public long update(T record, long recordUpdId) throws DuplicatedEntryException, DAOException {
		final String objectName = getObjectName(record);
		long totalEffectedRows;
		logger.debug("[START] : >>> --- Updating single '{}' informations with Id # {} ---", objectName, record.getId());
		try {
			record.setRecordUpdId(recordUpdId);
			totalEffectedRows = mapper.update(record);
		} catch (DuplicateKeyException e) {
			String errorMsg = "xxx " + DUPLICATE_KEY_UPDATE_FAILED_MSG + " xxx";
			throw new DuplicatedEntryException(errorMsg, e);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while updating '" + objectName + "' data ==> " + record + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Updating single '{}' informations with Id ---", objectName);
		return totalEffectedRows;
	}

	@Override
	public void update(List<T> records, long recordUpdId) throws DuplicatedEntryException, DAOException {
		final String objectName = getObjectName(records);
		logger.debug("[START] : >>> --- Updating multi '{}' informations ---", objectName);
		for (T record : records) {
			try {
				record.setRecordUpdId(recordUpdId);
				mapper.update(record);
			} catch (DuplicateKeyException e) {
				String errorMsg = "xxx " + DUPLICATE_KEY_UPDATE_FAILED_MSG + " xxx";
				throw new DuplicatedEntryException(errorMsg, e);
			} catch (Exception e) {
				String errorMsg = "xxx Error occured while updating '" + objectName + "' data ==> " + record + " xxx";
				throw new DAOException(errorMsg, e);
			}
		}
		logger.debug("[FINISH] : <<< --- Updating multi '{}' informations ---", objectName);
	}

	@Override
	public long update(C criteria, HashMap<String, Object> updateItems, long recordUpdId) throws DAOException, DuplicatedEntryException {
		long totalEffectedRows;
		final String objectName = getObjectName(criteria.getObjectClass());
		logger.debug("[START] : >>> --- Updating multi '{}' informations with criteria ==> {} ---", objectName, criteria);
		try {
			updateItems.put("recordUpdId", recordUpdId);
			totalEffectedRows = mapper.updateWithCriteria(criteria, updateItems);
		} catch (DuplicateKeyException e) {
			String errorMsg = "xxx " + DUPLICATE_KEY_UPDATE_FAILED_MSG + " xxx";
			throw new DuplicatedEntryException(errorMsg, e);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while updating multiple '" + objectName + "' informations [Values] ==> " + updateItems + " with [Criteria] ==> " + criteria + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Updating multi '{}' informations with criteria ---", objectName);
		return totalEffectedRows;
	}
}
