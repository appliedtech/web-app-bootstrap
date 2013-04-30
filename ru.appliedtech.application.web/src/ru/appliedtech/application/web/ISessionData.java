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
package ru.appliedtech.application.web;

import ru.appliedtech.application.AppException;

/**
 * Base interface for session data (both transient and serializable).
 * @param <P> The super-type of entries inside this object.
 * @author Igor Pavlenko
 */
public interface ISessionData<P> {
	
	/**
	 * Stores attribute in the session.
	 * @param name The name of the attribute.
	 * @param value The value of the attribute.
	 * @throws AppException
	 */
	void setAttribute(String name, P value) throws AppException;

	/**
	 * Removes attribute from the session.
	 * @param name The name of the attribute.
	 * @throws AppException
	 */
	void removeAttribute(String name) throws AppException;

	/**
	 * Gets attribute of the specified type from the session.
	 * @param <T> The type of the attribute.
	 * @param clazz The type of the attribute.
	 * @param name The name of the attribute.
	 * @return The value or <code>null</code>.
	 * @throws AppException
	 */
	<T extends P> T getAttribute(Class<T> clazz, String name) throws AppException;
}
