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
 *  	myapp-common-mybatis - InsertableRepositoryImpl.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 9:46 AM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

package com.github.cataclysmuprising.myapp.common.mybatis.repository;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.mybatis.mapper.base.InsertableMapper;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.InsertableRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.DUPLICATE_KEY_INSERT_FAILED_MSG;
import static com.github.cataclysmuprising.myapp.common.util.ObjectUtil.getObjectName;

public class InsertableRepositoryImpl<T extends BaseBean> implements InsertableRepository<T> {

	private static final Logger logger = LogManager.getLogger("repositoryLogs." + InsertableRepositoryImpl.class.getName());

	private InsertableMapper<T> mapper;

	public InsertableRepositoryImpl(InsertableMapper<T> mapper) {
		this.mapper = mapper;
	}

	@Override
	public long insert(T record, long recordRegId) throws DuplicatedEntryException, DAOException {
		final String objectName = getObjectName(record);
		logger.debug("[START] : >>> --- Inserting single {} information with data ==> {} ---", objectName, record);
		try {
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
		logger.debug("[FINISH] : <<< --- Inserting single {} informations with new Id # {} ---", objectName, record.getId());
		return record.getId();
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
			String errorMsg = "xxx Error occured while inserting multi " + objectName + " datas ==> " + records + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Inserting multi {} informations ---", objectName);
	}
}
