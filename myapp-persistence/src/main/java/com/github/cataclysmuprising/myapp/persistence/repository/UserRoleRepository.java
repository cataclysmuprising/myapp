/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-persistence - UserRoleRepository.java
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
package com.github.cataclysmuprising.myapp.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.cataclysmuprising.myapp.common.mybatis.repository.XGenericRepositoryImpl;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.XGenericRepository;
import com.github.cataclysmuprising.myapp.domain.bean.UserRoleBean;
import com.github.cataclysmuprising.myapp.domain.criteria.UserRoleCriteria;
import com.github.cataclysmuprising.myapp.persistence.mapper.UserRoleMapper;

@Repository
public class UserRoleRepository extends XGenericRepositoryImpl<UserRoleBean, UserRoleCriteria> implements XGenericRepository<UserRoleBean, UserRoleCriteria> {

    private static final Logger repositoryLogger = LogManager.getLogger("repositoryLogs." + UserRoleRepository.class.getName());

    private UserRoleMapper mapper;

    @Autowired
    public UserRoleRepository(UserRoleMapper mapper) {
        super(mapper);
        this.mapper = mapper;
    }

}
