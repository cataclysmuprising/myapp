/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-persistence - RoleServiceImpl.java
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
package com.github.cataclysmuprising.myapp.persistence.service;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_PREFIX;
import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_SUFFIX;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.mybatis.service.CommonGenericServiceImpl;
import com.github.cataclysmuprising.myapp.domain.bean.RoleBean;
import com.github.cataclysmuprising.myapp.domain.criteria.RoleCriteria;
import com.github.cataclysmuprising.myapp.persistence.repository.RoleRepository;
import com.github.cataclysmuprising.myapp.persistence.service.api.RoleService;

@Service
public class RoleServiceImpl extends CommonGenericServiceImpl<RoleBean, RoleCriteria> implements RoleService {

    private static final Logger serviceLogger = LogManager.getLogger("serviceLogs." + RoleServiceImpl.class.getName());

    private RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> selectRolesByActionUrl(String actionUrl) throws BusinessException {
        List<String> results;
        serviceLogger.info(LOG_PREFIX + "Transaction start for fetching 'RoleNames' by Action-Url process." + LOG_SUFFIX);
        try {
            results = repository.selectRolesByActionUrl(actionUrl);
        } catch (DAOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
        serviceLogger.info(LOG_PREFIX + "Transaction finished successfully for fetching 'RoleNames' by Action-Url process." + LOG_SUFFIX);
        return results;
    }
}
