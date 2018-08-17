/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-persistence - UserService.java
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.github.cataclysmuprising.myapp.persistence.service.api;

import java.util.Set;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.CommonGenericService;
import com.github.cataclysmuprising.myapp.domain.bean.AuthenticatedUserBean;
import com.github.cataclysmuprising.myapp.domain.bean.UserBean;
import com.github.cataclysmuprising.myapp.domain.criteria.UserCriteria;

public interface UserService extends CommonGenericService<UserBean, UserCriteria> {
    AuthenticatedUserBean selectAuthenticatedUser(String email) throws BusinessException;

    long createNewUserWithRoles(UserBean user, Set<Long> roleIds, long recordRegId) throws BusinessException, DuplicatedEntryException;
}
