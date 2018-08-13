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
 *  	myapp-common-mybatis - RemoveableRepositoryImpl.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 9:46 AM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

package com.github.cataclysmuprising.myapp.common.mybatis.repository;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.exception.ConsistencyViolationException;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.mybatis.mapper.base.RemoveableMapper;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.RemoveableRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.Assert;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.DATA_INTEGRITY_VIOLATION_MSG;
import static com.github.cataclysmuprising.myapp.common.util.ObjectUtil.getObjectName;

public class RemoveableRepositoryImpl<T extends BaseBean, C extends CommonCriteria> implements RemoveableRepository<T, C> {
	private static final Logger logger = LogManager.getLogger("repositoryLogs." + RemoveableRepositoryImpl.class.getName());

	private RemoveableMapper<T, C> mapper;

	public RemoveableRepositoryImpl(RemoveableMapper<T, C> mapper) {
		this.mapper = mapper;
	}

	@Override
	public long delete(long primaryKey, long recordUpdId) throws ConsistencyViolationException, DAOException {
		logger.debug("[START] : >>> --- Deleting single object information with primaryKey # {} ---", primaryKey);
		long totalEffectedRows;
		try {
			totalEffectedRows = mapper.deleteByPrimaryKey(primaryKey);
		} catch (DataIntegrityViolationException e) {
			String errorMsg = "xxx " + DATA_INTEGRITY_VIOLATION_MSG + " xxx";
			throw new ConsistencyViolationException(errorMsg, e);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while deleting with primaryKey ==> " + primaryKey + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Deleting single object information with primaryKey ---");
		return totalEffectedRows;
	}

	@Override
	public long delete(C criteria, long recordUpdId) throws ConsistencyViolationException, DAOException {
		Assert.notNull(criteria, "Criteria shouldn't be Null.");
		final String objectName = getObjectName(criteria.getObjectClass());
		long totalEffectedRows;
		logger.debug("[START] : >>> --- Deleting {} informations with criteria ==> {} ---", objectName, criteria);
		try {
			totalEffectedRows = mapper.deleteByCriteria(criteria);
		} catch (DataIntegrityViolationException e) {
			String errorMsg = "xxx " + DATA_INTEGRITY_VIOLATION_MSG + " xxx";
			throw new ConsistencyViolationException(errorMsg, e);
		} catch (Exception e) {
			String errorMsg = "xxx Error occured while deleting " + objectName + " data with criteria ==> " + criteria + " xxx";
			throw new DAOException(errorMsg, e);
		}
		logger.debug("[FINISH] : <<< --- Deleting {} informations with criteria  ---", objectName);
		return totalEffectedRows;
	}
}
