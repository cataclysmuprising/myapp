/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-persistence - StaticContentServiceImpl.java
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

import org.springframework.stereotype.Service;

import com.github.cataclysmuprising.myapp.common.mybatis.service.CommonGenericServiceImpl;
import com.github.cataclysmuprising.myapp.domain.bean.StaticContentBean;
import com.github.cataclysmuprising.myapp.domain.criteria.StaticContentCriteria;
import com.github.cataclysmuprising.myapp.persistence.repository.StaticContentRepository;
import com.github.cataclysmuprising.myapp.persistence.service.api.StaticContentService;

@Service
public class StaticContentServiceImpl extends CommonGenericServiceImpl<StaticContentBean, StaticContentCriteria> implements StaticContentService {
    public StaticContentServiceImpl(StaticContentRepository repository) {
        super(repository);
    }
}
