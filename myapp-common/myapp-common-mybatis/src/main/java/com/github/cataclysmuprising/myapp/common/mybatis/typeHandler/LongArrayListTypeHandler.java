/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-common-mybatis - LongArrayListTypeHandler.java
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
import java.util.ArrayList;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class LongArrayListTypeHandler extends BaseTypeHandler<ArrayList<Long>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ArrayList<Long> parameter, JdbcType jdbcType) throws SQLException {
        StringBuilder str = new StringBuilder(parameter.toString());
        ps.setString(i, str.substring(1, str.length() - 1));

    }

    @Override
    public ArrayList<Long> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);

        ArrayList<Long> results = new ArrayList<>();
        String[] arr = str.split(",");
        for (String s : arr) {
            if (s.trim().length() > 0) {
                results.add(Long.parseLong(s));
            }
        }
        return results;
    }

    @Override
    public ArrayList<Long> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);

        ArrayList<Long> results = new ArrayList<>();
        String[] arr = str.split(",");
        for (String s : arr) {
            if (s.trim().length() > 0) {
                results.add(Long.parseLong(s));
            }
        }

        return results;
    }

    @Override
    public ArrayList<Long> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);

        ArrayList<Long> results = new ArrayList<>();
        String[] arr = str.split(",");
        for (String s : arr) {
            if (s.trim().length() > 0) {
                results.add(Long.parseLong(s));
            }
        }

        return results;
    }

}
