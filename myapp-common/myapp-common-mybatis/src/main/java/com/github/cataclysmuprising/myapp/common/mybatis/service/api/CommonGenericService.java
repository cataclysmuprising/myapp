/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-common-mybatis - CommonGenericService.java
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
package com.github.cataclysmuprising.myapp.common.mybatis.service.api;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.domain.criteria.CommonCriteria;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.InsertableService;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.RemoveableService;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.SelectableService;
import com.github.cataclysmuprising.myapp.common.mybatis.service.api.root.UpdateableService;

public interface CommonGenericService<T extends BaseBean, C extends CommonCriteria> extends SelectableService<T, C>, InsertableService<T>, UpdateableService<T, C>, RemoveableService<T, C> {
}
