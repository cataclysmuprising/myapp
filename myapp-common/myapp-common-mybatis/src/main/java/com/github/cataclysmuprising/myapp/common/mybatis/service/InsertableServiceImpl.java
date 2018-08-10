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
 *  	myapp-common-mybatis - InsertableServiceImpl.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/10/18 1:26 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */
package com.github.cataclysmuprising.myapp.common.mybatis.service;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.InsertableRepository;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.InsertableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_PREFIX;
import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_SUFFIX;
import static com.github.cataclysmuprising.myapp.common.util.ObjectUtil.getObjectName;

public class InsertableServiceImpl<T extends BaseBean> implements InsertableService<T> {
	private static final Logger serviceLogger = LogManager.getLogger("serviceLogs." + InsertableServiceImpl.class.getName());
	private InsertableRepository<T> dao;

	public InsertableServiceImpl(InsertableRepository<T> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public long insert(T record, long recordRegId) throws DuplicatedEntryException, BusinessException {
		final String objectName = getObjectName(record);
		serviceLogger.info(LOG_PREFIX + "This transaction was initiated by User ID # {}" + LOG_SUFFIX, recordRegId);
		serviceLogger.info(LOG_PREFIX + "Transaction start for inserting {} informations." + LOG_SUFFIX, objectName);
		long lastInsertedId;
		try {
			lastInsertedId = dao.insert(record, recordRegId);
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
			dao.insert(records, recordRegId);
		} catch (DAOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		serviceLogger.info(LOG_PREFIX + "Transaction finished successfully for inserting multi {} informations." + LOG_SUFFIX, objectName);
	}
}
