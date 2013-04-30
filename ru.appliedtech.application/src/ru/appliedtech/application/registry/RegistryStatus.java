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

import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import ru.appliedtech.application.LocalizedStatus;
import ru.appliedtech.application.Status;


public class RegistryStatus extends Status {
	
	private static final Map<Integer, String> RESOURCE_KEYS = new TreeMap<Integer, String>();

	public static final RegistryStatus SUCCESS = new RegistryStatus(0, RegistryStatus.class.getName() + ".SUCCESS");
	public static final RegistryStatus FAILURE_UNKNOWN_REASON = new RegistryStatus(1, RegistryStatus.class.getName() + ".FAILURE_UNKNOWN_REASON");

	private RegistryStatus(int code, String resourceKey) {
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
