/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-common-domain - CommonCriteria.java
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
package com.github.cataclysmuprising.myapp.common.domain.criteria;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class CommonCriteria {
    private Long id;
    private long offset;
    private long limit;
    private String word;
    private List<Long> includeIds;
    private List<Long> excludeIds;
    private String orderBy;
    private Order order;

    public CommonCriteria() {
        offset = -1;
        limit = 0;
        order = Order.ASC;
    }

    public abstract Class<?> getObjectClass();

    public enum Order {
        ASC("ASC"), DESC("DESC");

        private String value;

        private Order(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
