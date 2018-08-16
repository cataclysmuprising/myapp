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
 *  	myapp-persistence - UserServiceImpl.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 4:06 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

package com.github.cataclysmuprising.myapp.persistence.service;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_PREFIX;
import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_SUFFIX;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.mybatis.service.CommonGenericServiceImpl;
import com.github.cataclysmuprising.myapp.domain.bean.AuthenticatedUserBean;
import com.github.cataclysmuprising.myapp.domain.bean.UserBean;
import com.github.cataclysmuprising.myapp.domain.bean.UserRoleBean;
import com.github.cataclysmuprising.myapp.domain.criteria.UserCriteria;
import com.github.cataclysmuprising.myapp.persistence.repository.UserRepository;
import com.github.cataclysmuprising.myapp.persistence.repository.UserRoleRepository;
import com.github.cataclysmuprising.myapp.persistence.service.api.UserService;

@Service
public class UserServiceImpl extends CommonGenericServiceImpl<UserBean, UserCriteria> implements UserService {
	private static final Logger serviceLogger = LogManager.getLogger("serviceLogs." + UserServiceImpl.class.getName());

	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	public UserServiceImpl(UserRepository repository) {
		super(repository);
		this.userRepository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public AuthenticatedUserBean selectAuthenticatedUser(String email) throws BusinessException {
		serviceLogger.info(LOG_PREFIX + "Transaction start for fetching Authenticated UserInfo by Email # " + email + LOG_SUFFIX);
		AuthenticatedUserBean record = null;
		try {
			record = userRepository.selectAuthenticatedUser(email);
		}
		catch (DAOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		serviceLogger.info(LOG_PREFIX + "Transaction finished successfully for fetching Authenticated UserInfo." + LOG_SUFFIX);
		return record;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public long createNewUserWithRoles(UserBean user, Set<Long> roleIds, long recordRegId) throws BusinessException, DuplicatedEntryException {
		final String objectName = "User";
		serviceLogger.info(LOG_PREFIX + "This transaction was initiated by User ID # {}" + LOG_SUFFIX, recordRegId);
		serviceLogger.info(LOG_PREFIX + "Transaction start for registering new {} informations." + LOG_SUFFIX, objectName);
		long lastInsertedId;
		try {
			lastInsertedId = userRepository.insert(user, recordRegId);
			if (roleIds != null && !roleIds.isEmpty()) {
				List<UserRoleBean> userRoles = new ArrayList<>();
				roleIds.forEach(roleId -> userRoles.add(new UserRoleBean(lastInsertedId, roleId)));
				userRoleRepository.insert(userRoles, recordRegId);
			}
			else {
				// register with 'User' Role
				userRoleRepository.insert(new UserRoleBean(lastInsertedId, 2L), recordRegId);
			}
		}
		catch (DAOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		serviceLogger.info(LOG_PREFIX + "Transaction finished successfully for registering new {} informations." + LOG_SUFFIX, objectName);
		return lastInsertedId;
	}
}
