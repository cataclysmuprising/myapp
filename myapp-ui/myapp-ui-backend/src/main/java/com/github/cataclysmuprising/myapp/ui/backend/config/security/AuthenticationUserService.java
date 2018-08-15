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
 *  	myapp-ui-backend - AuthenticationUserService.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/14/18 11:46 AM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */
package com.github.cataclysmuprising.myapp.ui.backend.config.security;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.domain.bean.AuthenticatedUserBean;
import com.github.cataclysmuprising.myapp.domain.bean.UserBean;
import com.github.cataclysmuprising.myapp.domain.criteria.UserCriteria;
import com.github.cataclysmuprising.myapp.persistence.service.api.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.*;

@Component
public class AuthenticationUserService implements UserDetailsService {
	private static final Logger applicationLogger = LogManager.getLogger("applicationLogs." + AuthenticationUserService.class.getName());
	private static final Logger errorLogger = LogManager.getLogger("errorLogs." + AuthenticationUserService.class.getName());

	@Autowired
	private UserService userService;

	@Override
	public final UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		applicationLogger.info(LOG_BREAKER_OPEN);
		try {
			AuthenticatedUserBean authUser = userService.selectAuthenticatedUser(email);
			if (authUser == null) {
				throw new UsernameNotFoundException("User doesn`t exist");
			}
			applicationLogger.info(LOG_PREFIX + "Roles of :" + authUser.getName() + " are " + authUser.getRoles() + LOG_SUFFIX);
			// pass authUser object and roles to LoggedUser
			LoggedUserBean loggedUser = new LoggedUserBean(authUser, authUser.getRoles());
			applicationLogger.info(LOG_BREAKER_CLOSE);
			return loggedUser;
		} catch (BusinessException e) {
			e.printStackTrace();
			errorLogger.error(LOG_PREFIX + "Signin authUser is not valid or found" + LOG_SUFFIX, e);
			return null;
		}
	}
}
