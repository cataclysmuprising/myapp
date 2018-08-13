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
 *  	myapp-common-mybatis - SelectableRepositoryImpl.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 3:44 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

package com.github.cataclysmuprising.myapp.common.mybatis.repository;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.mybatis.mapper.base.SelectableMapper;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.SelectableRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

import java.util.List;

import static com.github.cataclysmuprising.myapp.common.util.ObjectUtil.getObjectName;

public class SelectableRepositoryImpl<T extends BaseBean, C extends CommonCriteria> implements SelectableRepository<T, C> {

	private static final Logger logger = LogManager.getLogger("repositoryLogs." + SelectableRepositoryImpl.class.getName());

	private SelectableMapper<T, C> mapper;

	public SelectableRepositoryImpl(SelectableMapper<T, C> mapper) {
		this.mapper = mapper;
	}

	@Override
	public T select(long primaryKey) throws DAOException {
		logger.debug("[START] : >>> --- Fetching single object information with primaryKey # {} ---", primaryKey);
		T record;
		try {
			record = mapper.selectByPrimaryKey(primaryKey);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while fetching single object information with primaryKey ==> " + primaryKey + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Fetching single object information with primaryKey ---");
		return record;
	}

	@Override
	public T select(C criteria) throws DAOException {
		Assert.notNull(criteria, "Criteria shouldn't be Null.");
		final String objectName = getObjectName(criteria.getObjectClass());
		logger.debug("[START] : >>> --- Fetching single {} information with criteria ==> {} ---", objectName, criteria);
		T record;
		try {
			record = mapper.selectSingleRecord(criteria);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while fetching single " + objectName + " information with criteria ==> " + criteria + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Fetching single 'User' information with criteria ---");
		return record;
	}

	@Override
	public List<T> selectList(C criteria) throws DAOException {
		Assert.notNull(criteria, "Criteria shouldn't be Null.");
		final String objectName = getObjectName(criteria.getObjectClass());
		logger.debug("[START] : >>> --- Fetching multi {} informations with criteria ==> {} ---", objectName, criteria);
		List<T> records;
		try {
			records = mapper.selectMultiRecords(criteria);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while fetching multiple " + objectName + " informations with criteria ==> " + criteria + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Fetching multi {} informations with criteria ---", objectName);
		return records;
	}

	@Override
	public long selectCounts(C criteria) throws DAOException {
		Assert.notNull(criteria, "Criteria shouldn't be Null.");
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
