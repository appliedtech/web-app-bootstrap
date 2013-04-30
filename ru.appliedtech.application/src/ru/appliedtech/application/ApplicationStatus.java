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
package ru.appliedtech.application;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ApplicationStatus extends Status {
	
	private static final Map<Integer, String> RESOURCE_KEYS = new TreeMap<Integer, String>();

	public static final ApplicationStatus SUCCESS = new ApplicationStatus(0, ApplicationStatus.class.getName() + ".SUCCESS");
	public static final ApplicationStatus FAILURE_UNKNOWN_REASON = new ApplicationStatus(1, ApplicationStatus.class.getName() + ".FAILURE_UNKNOWN_REASON");

	private ApplicationStatus(int code, String resourceKey) {
		super(code);
		RESOURCE_KEYS.put(code, resourceKey);
	}

	@Override
	public LocalizedStatus createLocalizedStatus(ResourceBundle resources) {
		return new LocalizedStatus(this, resources.getString(RESOURCE_KEYS.get(this.code)));
	}

	@Override
	public boolean isSuccess() {
		return code == 0;
	}
}
