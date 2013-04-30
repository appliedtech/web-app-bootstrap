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

public class ValidationUtils {
	
	public static abstract class Validator<T> {
		public boolean isValid(T param) {
			try {
				requireValid(param);
				return true;
			} catch (AppException e) {
				return false;
			}
		}
		public abstract T requireValid(T param) throws AppException;
	}
	
	public static Validator<String> PATH_ELEMENT = new Validator<String>() {
		@Override
		public String requireValid(String param) throws AppException {
			if (param == null || param.contains("/") || param.contains("\\") || param.contains("\'") || param.contains("\"")) {
				throw new AppException(ApplicationStatus.FAILURE_UNKNOWN_REASON);
			}
			return param;
		}
	};
	
	public static Validator<String> PATH_FRAGMENT = new Validator<String>() {
		@Override
		public String requireValid(String param) throws AppException {
			if (param == null || param.contains("/../") || param.contains("\\..\\")) {
				throw new AppException(ApplicationStatus.FAILURE_UNKNOWN_REASON);
			}
			return param;
		}
	};
}
