/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-common-mybatis - CommonGenericServiceImpl.java
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
package com.github.cataclysmuprising.myapp.common.mybatis.service;

import java.util.HashMap;
import java.util.List;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.ConsistencyViolationException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.CommonGenericRepository;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.CommonGenericService;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.InsertableService;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.RemoveableService;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.SelectableService;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.UpdateableService;

public class CommonGenericServiceImpl<T extends BaseBean, C extends CommonCriteria> implements CommonGenericService<T, C> {

    private SelectableService<T, C> selectableService;
    private InsertableService<T> insertableService;
    private UpdateableService<T, C> updateableService;
    private RemoveableService<T, C> removeableService;

    public CommonGenericServiceImpl(CommonGenericRepository<T, C> dao) {
        selectableService = new SelectableServiceImpl<>(dao);
        insertableService = new InsertableServiceImpl<>(dao);
        updateableService = new UpdateableServiceImpl<>(dao);
        removeableService = new RemoveableServiceImpl<>(dao);
    }

    @Override
    public T select(long primaryKey) throws BusinessException {
        return selectableService.select(primaryKey);
    }

    @Override
    public T select(C criteria) throws BusinessException {
        return selectableService.select(criteria);
    }

    @Override
    public List<T> selectList(C criteria) throws BusinessException {
        return selectableService.selectList(criteria);
    }

    @Override
    public long selectCounts(C criteria) throws BusinessException {
        return selectableService.selectCounts(criteria);
    }

    @Override
    public long insert(T record, long recordRegId) throws DuplicatedEntryException, BusinessException {
        return insertableService.insert(record, recordRegId);
    }

    @Override
    public void insert(List<T> records, long recordRegId) throws DuplicatedEntryException, BusinessException {
        insertableService.insert(records, recordRegId);
    }

    @Override
    public long update(T record, long recordUpdId) throws DuplicatedEntryException, BusinessException {
        return updateableService.update(record, recordUpdId);
    }

    @Override
    public void update(List<T> records, long recordUpdId) throws DuplicatedEntryException, BusinessException {
        updateableService.update(records, recordUpdId);
    }

    @Override
    public long update(C criteria, HashMap<String, Object> updateItems, long recordUpdId) throws BusinessException, DuplicatedEntryException {
        return updateableService.update(criteria, updateItems, recordUpdId);
    }

    @Override
    public long delete(long id, long recordUpdId) throws ConsistencyViolationException, BusinessException {
        return removeableService.delete(id, recordUpdId);
    }

    @Override
    public long delete(C criteria, long recordUpdId) throws ConsistencyViolationException, BusinessException {
        return removeableService.delete(criteria, recordUpdId);
    }
}
