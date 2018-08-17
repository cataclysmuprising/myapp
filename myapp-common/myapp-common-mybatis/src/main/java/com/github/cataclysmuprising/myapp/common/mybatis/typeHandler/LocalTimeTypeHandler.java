/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-common-mybatis - LocalTimeTypeHandler.java
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
import java.sql.Time;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

@MappedTypes(LocalTime.class)
public class LocalTimeTypeHandler implements TypeHandler<LocalTime> {

    @Override
    public void setParameter(PreparedStatement ps, int i, LocalTime parameter, JdbcType jdbcType) throws SQLException {

        if (parameter != null) {
            DateTime datetime = new DateTime(1970, 1, 1, parameter.getHourOfDay(), parameter.getMinuteOfHour(), parameter.getSecondOfMinute(), 0);
            ps.setTime(i, new Time(datetime.toDate().getTime()));
        } else {
            ps.setTime(i, null);
        }
    }

    @Override
    public LocalTime getResult(ResultSet rs, String columnName) throws SQLException {
        Time time = rs.getTime(columnName);
        if (time != null) {
            return new LocalTime(time.getTime());
        } else {
            return null;
        }

    }

    @Override
    public LocalTime getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Time time = cs.getTime(columnIndex);
        if (time != null) {
            return new LocalTime(time.getTime());
        } else {
            return null;
        }
    }

    @Override
    public LocalTime getResult(ResultSet rs, int columnIndex) throws SQLException {
        Time time = rs.getTime(columnIndex);
        if (time != null) {
            return new LocalTime(time.getTime());
        } else {
            return null;
        }
    }

}
