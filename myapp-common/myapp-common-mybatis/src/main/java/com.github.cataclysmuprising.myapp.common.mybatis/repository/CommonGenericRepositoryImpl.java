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
 *  	myapp-common-mybatis - CommonGenericRepositoryImpl.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/10/18 1:53 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

package com.github.cataclysmuprising.myapp.common.mybatis.repository;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.exception.ConsistencyViolationException;
import com.github.cataclysmuprising.myapp.common.exception.DAOException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.mybatis.mapper.base.CommonGenericMapper;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.CommonGenericRepository;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.InsertableRepository;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.RemoveableRepository;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.SelectableRepository;
import com.github.cataclysmuprising.myapp.common.mybatis.repository.api.root.UpdateableRepository;

import java.util.HashMap;
import java.util.List;

public class CommonGenericRepositoryImpl<T extends BaseBean, C extends CommonCriteria> implements CommonGenericRepository<T, C> {

	private InsertableRepository<T> insertableRepository;
	private UpdateableRepository<T, C> updateableRepository;
	private RemoveableRepository<T, C> removeableRepository;
	private SelectableRepository<T, C> selectableRepository;

	public CommonGenericRepositoryImpl(CommonGenericMapper<T, C> mapper) {
		insertableRepository = new InsertableRepositoryImpl<>(mapper);
		updateableRepository = new UpdateableRepositoryImpl<>(mapper);
		removeableRepository = new RemoveableRepositoryImpl<>(mapper);
		selectableRepository = new SelectableRepositoryImpl<>(mapper);
	}

	@Override
	public long insert(T record, long recordRegId) throws DuplicatedEntryException, DAOException {
		return insertableRepository.insert(record, recordRegId);
	}

	@Override
	public void insert(List<T> records, long recordRegId) throws DuplicatedEntryException, DAOException {
		insertableRepository.insert(records, recordRegId);
	}

	@Override
	public long delete(long primaryKey, long recordUpdId) throws ConsistencyViolationException, DAOException {
		return removeableRepository.delete(primaryKey, recordUpdId);
	}

	@Override
	public long delete(C criteria, long recordUpdId) throws ConsistencyViolationException, DAOException {
		return removeableRepository.delete(criteria, recordUpdId);
	}

	@Override
	public T select(long primaryKey) throws DAOException {
		return selectableRepository.select(primaryKey);
	}

	@Override
	public T select(C criteria) throws DAOException {
		return selectableRepository.select(criteria);
	}

	@Override
	public List<T> selectList(C criteria) throws DAOException {
		return selectableRepository.selectList(criteria);
	}

	@Override
	public long selectCounts(C criteria) throws DAOException {
		return selectableRepository.selectCounts(criteria);
	}

	@Override
	public long update(T record, long recordUpdId) throws DuplicatedEntryException, DAOException {
		return updateableRepository.update(record, recordUpdId);
	}

	@Override
	public void update(List<T> records, long recordUpdId) throws DuplicatedEntryException, DAOException {
		updateableRepository.update(records, recordUpdId);
	}

	@Override
	public long update(C criteria, HashMap<String, Object> updateItems, long recordUpdId) throws DAOException, DuplicatedEntryException {
		return updateableRepository.update(criteria, updateItems, recordUpdId);
	}
}
