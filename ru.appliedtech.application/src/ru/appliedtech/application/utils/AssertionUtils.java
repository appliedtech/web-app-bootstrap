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
package ru.appliedtech.application.utils;

import ru.appliedtech.application.AppException;
import ru.appliedtech.application.ApplicationStatus;

/**
 * Helper class for assertion checking.
 * @author Igor Pavlenko
 */
public class AssertionUtils {
	
	/**
	 * Throws the exception if the <code>value</code> equals to <code>null</code>. Returns the value.
	 * @param <T> The type of the value.
	 * @param value The value for checking.
	 * @param message The message for the exception
	 * @return The same <code>value</code>.
	 * @throws AppException If the value is <code>null</code>
	 */
	public static <T> T assertNotNull(T value, String message) throws AppException {
		if (value == null) {
			throw new AppException(message, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		}
		return value;
	}

	/**
	 * Throws the exception if the <code>condition</code> is not <code>true</code>. Returns the condition.
	 * @param condition The condition.
	 * @param message The message for the exception.
	 * @return The same <code>condition</code>.
	 * @throws AppException If the <code>condition</code> is <code>false</code>
	 */
	public static boolean assertTrue(boolean condition, String message) throws AppException {
		if (!condition) {
			throw new AppException(message, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		}
		return true;
	}
}
