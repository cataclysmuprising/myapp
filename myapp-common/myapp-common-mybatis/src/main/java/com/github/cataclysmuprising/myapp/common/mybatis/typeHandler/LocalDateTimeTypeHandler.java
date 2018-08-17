/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-common-mybatis - LocalDateTimeTypeHandler.java
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
package com.github.cataclysmuprising.myapp.common.mybatis.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

public class LocalDateTimeTypeHandler implements TypeHandler {
    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, new java.sql.Timestamp(((LocalDateTime) parameter).toDateTime(DateTimeZone.getDefault()).toDate().getTime()));
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        return new LocalDateTime(rs.getTimestamp(columnName).getTime(), DateTimeZone.getDefault());
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new LocalDateTime(cs.getTimestamp(columnIndex).getTime(), DateTimeZone.getDefault());
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        return new LocalDateTime(rs.getTimestamp(columnIndex).getTime(), DateTimeZone.getDefault());
    }
}
