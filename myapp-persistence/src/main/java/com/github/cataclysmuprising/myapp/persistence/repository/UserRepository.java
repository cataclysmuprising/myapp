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
 *  	myapp-persistence - UserRepository.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 12:41 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */
package com.github.cataclysmuprising.myapp.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.CommonGenericRepositoryImpl;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.CommonGenericRepository;
import com.github.cataclysmuprising.myapp.domain.bean.AuthenticatedUserBean;
import com.github.cataclysmuprising.myapp.domain.bean.UserBean;
import com.github.cataclysmuprising.myapp.domain.criteria.UserCriteria;
import com.github.cataclysmuprising.myapp.persistence.mapper.UserMapper;

@Repository
public class UserRepository extends CommonGenericRepositoryImpl<UserBean, UserCriteria> implements CommonGenericRepository<UserBean, UserCriteria> {

	private static final Logger repositoryLogger = LogManager.getLogger("repositoryLogs." + UserRepository.class.getName());

	private UserMapper mapper;

	@Autowired
	public UserRepository(UserMapper mapper) {
		super(mapper);
		this.mapper = mapper;
	}

	public AuthenticatedUserBean selectAuthenticatedUser(String email) throws DAOException {
		repositoryLogger.debug("[START] : >>> --- Fetching Authenticated 'User' informations with Email # " + email + " ---");
		AuthenticatedUserBean user = new AuthenticatedUserBean();
		try {
			user = mapper.selectAuthenticatedUser(email);
		}
		catch (Exception e) {
			String errorMsg = "xxx Error occured while fetching Authenticated 'User' informations with Email ==> " + email + " xxx";
			throw new DAOException(errorMsg, e);
		}
		repositoryLogger.debug("[FINISH] : <<< --- Fetching Authenticated 'User' informations with Email # " + email + " ---");
		return user;
	}
}
