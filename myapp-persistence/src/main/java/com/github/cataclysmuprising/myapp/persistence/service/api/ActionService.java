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
 *  	myapp-persistence - ActionService.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/13/18 3:53 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

package com.github.cataclysmuprising.myapp.persistence.service.api;

import java.util.List;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.SelectableService;
import com.github.cataclysmuprising.myapp.domain.bean.ActionBean;
import com.github.cataclysmuprising.myapp.domain.criteria.ActionCriteria;

public interface ActionService extends SelectableService<ActionBean, ActionCriteria> {
	List<String> selectAvailableActionsForUser(String pageName, List<Long> roleIds) throws BusinessException;
}
