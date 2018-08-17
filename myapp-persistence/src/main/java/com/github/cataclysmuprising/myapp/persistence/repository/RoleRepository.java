/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-persistence - RoleRepository.java
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

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.CommonGenericRepositoryImpl;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.CommonGenericRepository;
import com.github.cataclysmuprising.myapp.domain.bean.RoleBean;
import com.github.cataclysmuprising.myapp.domain.criteria.RoleCriteria;
import com.github.cataclysmuprising.myapp.persistence.mapper.RoleMapper;

@Repository
public class RoleRepository extends CommonGenericRepositoryImpl<RoleBean, RoleCriteria> implements CommonGenericRepository<RoleBean, RoleCriteria> {

    private static final Logger repositoryLogger = LogManager.getLogger("repositoryLogs." + RoleRepository.class.getName());

    private RoleMapper mapper;

    @Autowired
    public RoleRepository(RoleMapper mapper) {
        super(mapper);
        this.mapper = mapper;
    }

    public List<String> selectRolesByActionUrl(String actionUrl) throws DAOException {
        repositoryLogger.debug("[START] : >>> --- Fetching all 'RoleNames' by actionUrl = '" + actionUrl + "' ---");
        List<String> roleNames = null;
        try {
            roleNames = mapper.selectRolesByActionUrl(actionUrl);
        } catch (Exception e) {
            String errorMsg = "xxx Error occured while fetching all 'RoleNames' by actionUrl = '" + actionUrl + "' ---";
            throw new DAOException(errorMsg, e);
        }
        repositoryLogger.debug("[FINISH] : <<< --- Fetching all 'RoleNames' by actionUrl = '" + actionUrl + "' ---");
        return roleNames;
    }
}
