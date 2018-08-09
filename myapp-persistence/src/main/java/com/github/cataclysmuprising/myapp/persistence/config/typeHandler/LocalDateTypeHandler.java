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
 *  	myapp-persistence - LocalDateTypeHandler.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/9/18 9:20 AM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

package com.github.cataclysmuprising.myapp.persistence.config.typeHandler;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

@MappedTypes(LocalDate.class)
public class LocalDateTypeHandler implements TypeHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.type.TypeHandler#setParameter(java.sql.
	 * PreparedStatement, int, java.lang.Object,
	 * org.apache.ibatis.type.JdbcType)
	 */
	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {

		LocalDate date = (LocalDate) parameter;
		if (date != null) {
			ps.setDate(i, new Date(date.toDateTimeAtStartOfDay().toDate().getTime()));
		}
		else {
			ps.setDate(i, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet,
	 * java.lang.String)
	 */
	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		Date date = rs.getDate(columnName);
		if (date != null) {
			return new LocalDate(date.getTime(), DateTimeZone.getDefault());
		}
		else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.type.TypeHandler#getResult(java.sql.CallableStatement,
	 * int)
	 */
	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		Date date = cs.getDate(columnIndex);
		if (date != null) {
			return new LocalDate(date.getTime(), DateTimeZone.getDefault());
		}
		else {
			return null;
		}
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		Date date = rs.getDate(columnIndex);
		if (date != null) {
			return new LocalDate(date.getTime(), DateTimeZone.getDefault());
		}
		else {
			return null;
		}
	}

}
