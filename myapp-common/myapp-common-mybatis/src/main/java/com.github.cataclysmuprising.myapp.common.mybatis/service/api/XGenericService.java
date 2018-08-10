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
 *  	myapp-common-mybatis - XGenericService.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/10/18 3:05 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */
package com.github.cataclysmuprising.myapp.common.mybatis.service.api;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.ConsistencyViolationException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;

import java.util.List;
import java.util.Set;

public interface XGenericService<T extends BaseBean, C extends CommonCriteria> {

	void insert(T record, long recordRegId) throws DuplicatedEntryException, BusinessException;

	void insert(List<T> records, long recordRegId) throws DuplicatedEntryException, BusinessException;

	void insert(long key1, long key2, long recordRegId) throws DuplicatedEntryException, BusinessException;

	long delete(long key1, long key2, long recordUpdId) throws ConsistencyViolationException, BusinessException;

	long delete(C criteria, long recordUpdId) throws ConsistencyViolationException, BusinessException;

	Set<Long> selectRelatedKeysByKey1(long key1) throws BusinessException;

	Set<Long> selectRelatedKeysByKey2(long key2) throws BusinessException;

	T select(long key1, long key2) throws BusinessException;

	List<T> selectList(C criteria) throws BusinessException;

	long selectCounts(C criteria) throws BusinessException;
}
