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
import java.util.ResourceBundle;
import java.util.TreeMap;

import ru.appliedtech.application.LocalizedStatus;
import ru.appliedtech.application.Status;

public class SecurityStatus extends Status {

	private static final Map<Integer, String> RESOURCE_KEYS = new TreeMap<Integer, String>();

	public static final SecurityStatus SUCCESS = new SecurityStatus(0, SecurityStatus.class.getName() + ".SUCCESS");
	public static final SecurityStatus FAILURE_UNKNOWN_REASON = new SecurityStatus(1, SecurityStatus.class.getName() + ".FAILURE_UNKNOWN_REASON");
	
	public static final SecurityStatus FAILURE_EMAIL_EMPTY = new SecurityStatus(2, SecurityStatus.class.getName() + ".FAILURE_EMAIL_EMPTY");
	public static final SecurityStatus FAILURE_USER_EXISTS = new SecurityStatus(3, SecurityStatus.class.getName() + ".FAILURE_USER_EXISTS");
	public static final SecurityStatus FAILURE_EMAIL_INVALID = new SecurityStatus(4, SecurityStatus.class.getName() + ".FAILURE_EMAIL_INVALID");
	public static final SecurityStatus FAILURE_PASSWORD_EMPTY = new SecurityStatus(5, SecurityStatus.class.getName() + ".FAILURE_PASSWORD_EMPTY");
	public static final SecurityStatus FAILURE_PASSWORD_TOO_SHORT = new SecurityStatus(6, SecurityStatus.class.getName() + ".FAILURE_PASSWORD_TOO_SHORT");
	public static final SecurityStatus FAILURE_PASSWORD_TOO_SIMPLE = new SecurityStatus(7, SecurityStatus.class.getName() + ".FAILURE_PASSWORD_TOO_SIMPLE");
	public static final SecurityStatus FAILURE_CONFIRMATION_FAILED = new SecurityStatus(8, SecurityStatus.class.getName() + ".FAILURE_CONFIRMATION_FAILED");
	public static final SecurityStatus FAILURE_NOT_PERMITTED = new SecurityStatus(9, SecurityStatus.class.getName() + ".FAILURE_NOT_PERMITTED");

	public static final SecurityStatus FAILURE_USER_NOT_FOUNDED = new SecurityStatus(10, SecurityStatus.class.getName() + ".FAILURE_USER_NOT_FOUNDED");
	public static final SecurityStatus FAILURE_USER_NOT_VALIDATED = new SecurityStatus(11, SecurityStatus.class.getName() + ".FAILURE_USER_NOT_VALIDATED");
	public static final SecurityStatus FAILURE_USER_AUTH = new SecurityStatus(12, SecurityStatus.class.getName() + ".FAILURE_USER_AUTH");
	public static final SecurityStatus FAILURE_CANNOT_HASH_PASSWORD = new SecurityStatus(13, SecurityStatus.class.getName() + ".FAILURE_CANNOT_HASH_PASSWORD");
	public static final SecurityStatus FAILURE_CANNOT_ENCRYPT_USER_DATA = new SecurityStatus(14, SecurityStatus.class.getName() + ".FAILURE_CANNOT_ENCRYPT_USER_DATA");
	public static final SecurityStatus FAILURE_CANNOT_DECRYPT_USER_DATA = new SecurityStatus(15, SecurityStatus.class.getName() + ".FAILURE_CANNOT_DECRYPT_USER_DATA");
	
	private SecurityStatus(int code, String resourceKey) {
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
