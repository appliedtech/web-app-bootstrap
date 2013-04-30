/*
* Copyright (c) 2013 Applied Technologies, Ltd.
*
* Permission is hereby granted, free of charge, to any person obtaining
* a copy of this software and associated documentation files (the
* "Software"), to deal in the Software without restriction, including
* without limitation the rights to use, copy, modify, merge, publish,
* distribute, sublicense, and/or sell copies of the Software, and to
* permit persons to whom the Software is furnished to do so, subject to
* the following conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
* LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
* OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
* WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
*/
package ru.appliedtech.storage;

import java.io.ByteArrayInputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;

import lombok.Delegate;

public class CallableStatementExt implements CallableStatement {

	// https://code.google.com/p/projectlombok/issues/detail?id=305
	private final @Delegate(types={CallableStatement.class, Statement.class}) CallableStatement statement;

	public CallableStatementExt(CallableStatement statement) {
		this.statement = statement;
	}

	/**
	 * Sets the value of a nullable DATE field.
	 * @param parameterIndex The index of a field.
	 * @param value The value.
	 * @throws SQLException
	 */
	public void setNullableDate(int parameterIndex, Date value) throws SQLException {
		if (value == null) {
			statement.setNull(parameterIndex, Types.DATE);
		} else {
			statement.setDate(parameterIndex, new java.sql.Date(value.getTime()));
		}
	}

	/**
	 * Sets the value of a nullable TIMESTAMP field.
	 * @param parameterIndex The index of a field.
	 * @param value The value.
	 * @throws SQLException
	 */
	public void setNullableTimestamp(int parameterIndex, Date value) throws SQLException {
		if (value == null) {
			statement.setNull(parameterIndex, Types.TIMESTAMP);
		} else {
			statement.setTimestamp(parameterIndex, new java.sql.Timestamp(value.getTime()));
		}
	}

	/**
	 * Sets the value of a nullable STRING field.
	 * @param parameterIndex The index of a field.
	 * @param value The value.
	 * @throws SQLException
	 */
	public void setNullableVarchar(int parameterIndex, String value) throws SQLException {
		if (value == null) {
			statement.setNull(parameterIndex, Types.VARCHAR);
		} else {
			statement.setString(parameterIndex, value);
		}
	}

	/**
	 * Sets the value of a nullable VARBINARY field.
	 * @param parameterIndex The index of a field.
	 * @param value The value.
	 * @throws SQLException
	 */
	public void setNullableVarbinary(int parameterIndex, byte[] value) throws SQLException {
		if (value == null) {
			statement.setNull(parameterIndex, Types.VARBINARY);
		} else {
			statement.setBytes(parameterIndex, value);
		}
	}

	/**
	 * Sets the value of a nullable BLOB field.
	 * @param parameterIndex The index of a field.
	 * @param value The value.
	 * @throws SQLException
	 */
	public void setNullableBlob(int parameterIndex, byte[] value) throws SQLException {
		if (value == null) {
			statement.setNull(parameterIndex, Types.BLOB);
		} else {
			statement.setBinaryStream(parameterIndex, new ByteArrayInputStream(value));
		}
	}

	/**
	 * Sets the value of a nullable FLOAT field.
	 * @param parameterIndex The index of a field.
	 * @param value The value.
	 * @throws SQLException
	 */
	public void setNullableFloat(int parameterIndex, Float value) throws SQLException {
		if (value == null) {
			statement.setNull(parameterIndex, Types.FLOAT);
		} else {
			statement.setFloat(parameterIndex, value);
		}
	}

	/**
	 * Sets the value of a nullable LONG field.
	 * @param parameterIndex The index of a field.
	 * @param value The value.
	 * @throws SQLException
	 */
	public void setNullableLong(int parameterIndex, Long value) throws SQLException {
		if (value == null) {
			statement.setNull(parameterIndex, Types.BIGINT);
		} else {
			statement.setLong(parameterIndex, value);
		}
	}
}
