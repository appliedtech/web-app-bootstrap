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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ru.appliedtech.application.AppException;


/**
 * Session storage for {@link Serializable} data.
 * @author Igor Pavlenko
 */
public class SessionData implements ISessionData<Serializable>, Serializable {
	private static final long serialVersionUID = -6155410003784800692L;

	private final Map<String, Serializable> attributes = new HashMap<String, Serializable>();

	public SessionData() {
		// for serialization
	}

	@Override
	public synchronized void setAttribute(String name, Serializable value) {
		attributes.put(name, value);
	}

	@Override
	public synchronized <T extends Serializable> T getAttribute(Class<T> clazz, String name) {
		Serializable value = attributes.get(name);
		if (clazz.isInstance(value)) {
			return clazz.cast(value);
		}
		return null;
	}

	@Override
	public void removeAttribute(String name) throws AppException {
		this.attributes.remove(name);
	}
}
