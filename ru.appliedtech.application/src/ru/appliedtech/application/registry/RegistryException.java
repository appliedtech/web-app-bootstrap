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
package ru.appliedtech.application.registry;

import ru.appliedtech.application.AppException;

/**
 * Base exception for all {@link Registry}'s exceptions.
 * @author Igor Pavlenko
 */
public class RegistryException extends AppException
{
	private static final long serialVersionUID = 4659903708514428341L;

	/**
	 * Constructs a new registry exception with the specified detail message and cause.
	 * @param message The message.
	 * @param cause The cause.
	 */
	public RegistryException(String message, Throwable cause, RegistryStatus status)
	{
		super(message, cause, status);
	}

	/**
	 * Constructs a new registry exception with the specified cause.
	 * @param cause The cause.
	 */
	public RegistryException(Throwable cause, RegistryStatus status)
	{
		super(cause, status);
	}

	/**
	 * Constructs a new registry exception with the specified detail message.
	 * @param message The message.
	 */
	public RegistryException(String message, RegistryStatus status)
	{
		super(message, status);
	}

	/**
	 * Constructs a new registry exception without detail message.
	 */
	protected RegistryException(RegistryStatus status)
	{
		super(status);
	}
}
