/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-persistence - UserRepository.java
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

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.DATA_INTEGRITY_VIOLATION_MSG;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.github.cataclysmuprising.myapp.common.exception.ConsistencyViolationException;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.CommonGenericRepositoryImpl;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.CommonGenericRepository;
import com.github.cataclysmuprising.myapp.domain.bean.AuthenticatedUserBean;
import com.github.cataclysmuprising.myapp.domain.bean.UserBean;
import com.github.cataclysmuprising.myapp.domain.criteria.UserCriteria;
import com.github.cataclysmuprising.myapp.domain.criteria.UserRoleCriteria;
import com.github.cataclysmuprising.myapp.persistence.mapper.UserMapper;
import com.github.cataclysmuprising.myapp.persistence.mapper.UserRoleMapper;

@Repository
public class UserRepository extends CommonGenericRepositoryImpl<UserBean, UserCriteria> implements CommonGenericRepository<UserBean, UserCriteria> {

    private static final Logger repositoryLogger = LogManager.getLogger("repositoryLogs." + UserRepository.class.getName());

    private UserMapper mapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

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
        } catch (Exception e) {
            String errorMsg = "xxx Error occured while fetching Authenticated 'User' informations with Email ==> " + email + " xxx";
            throw new DAOException(errorMsg, e);
        }
        repositoryLogger.debug("[FINISH] : <<< --- Fetching Authenticated 'User' informations with Email # " + email + " ---");
        return user;
    }

    @Override
    public long delete(long primaryKey, long recordUpdId) throws ConsistencyViolationException, DAOException {
        repositoryLogger.debug("[START] : >>> --- Deleting single 'User' information with primaryKey # {} ---", primaryKey);
        long totalEffectedRows;
        try {
            repositoryLogger.debug("[START] : $1 --- Removing related 'User-Role' relations to remove 'User' by given userId ---", primaryKey);
            UserRoleCriteria userRoleCriteria = new UserRoleCriteria();
            userRoleCriteria.setUserId(primaryKey);
            userRoleMapper.deleteByCriteria(userRoleCriteria);
            repositoryLogger.debug("[FINISH] : $1 --- Removing related 'User-Role' relations to remove 'User' by given userId ---", primaryKey);
            totalEffectedRows = mapper.deleteByPrimaryKey(primaryKey);
        } catch (DataIntegrityViolationException e) {
            String errorMsg = "xxx " + DATA_INTEGRITY_VIOLATION_MSG + " xxx";
            throw new ConsistencyViolationException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "xxx Error occured while deleting with primaryKey ==> " + primaryKey + " xxx";
            throw new DAOException(errorMsg, e);
        }
        repositoryLogger.debug("[FINISH] : <<< --- Deleting single 'User' information with primaryKey ---");
        return totalEffectedRows;
    }
}
