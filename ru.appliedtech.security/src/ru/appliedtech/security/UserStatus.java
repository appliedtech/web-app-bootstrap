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
package ru.appliedtech.security;

import java.util.Map;
import java.util.TreeMap;

import ru.appliedtech.application.ElementType;

public class UserStatus extends ElementType<UserStatus> {
	
	private static final Map<Integer, UserStatus> elements = new TreeMap<>();

	private UserStatus(int code, String name) {
		super(code, name);
		elements.put(code, this);
	}

	public static final UserStatus NEW = new UserStatus(1, "NEW");
	public static final UserStatus VALIDATED = new UserStatus(2, "VALIDATED");
	
	public static final int MASK_ANY = NEW.code | VALIDATED.code;
	
	public static UserStatus valueOf(Integer code) {
		return elements.get(code);
	}
}
