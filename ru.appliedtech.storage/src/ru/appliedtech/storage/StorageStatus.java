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

import java.text.MessageFormat;
import java.util.ResourceBundle;

import ru.appliedtech.application.LocalizedStatus;
import ru.appliedtech.application.Status;


/**
 * Localizable status for storage exceptions.
 * @author Igor Pavlenko
 */
public class StorageStatus extends Status {
	
	public static final int ENTITY_NOT_FOUND_CODE = 2;
	public static final int BAD_REQUEST_CODE = 3;
	public static final int NOT_MODIFIED_CODE = 4;
	public static final int FORBIDDEN_CODE = 5;
	public static final int TITLE_UNIQUE_CONSTRAINT_VIOLATION_CODE = 6;
	
	public static final StorageStatus SUCCESS = new StorageStatus(0, StorageStatus.class.getName() + ".SUCCESS");
	public static final StorageStatus FAILURE_UNKNOWN_REASON = new StorageStatus(1, StorageStatus.class.getName() + ".FAILURE_UNKNOWN_REASON");
	public static final StorageStatus ENTITY_NOT_FOUND = new StorageStatus(ENTITY_NOT_FOUND_CODE, StorageStatus.class.getName() + ".ENTITY_NOT_FOUND");
	public static final StorageStatus BAD_REQUEST = new StorageStatus(BAD_REQUEST_CODE, StorageStatus.class.getName() + ".BAD_REQUEST");
	public static final StorageStatus BAD_REQUEST_DATA = new StorageStatus(BAD_REQUEST_CODE, StorageStatus.class.getName() + ".BAD_REQUEST_DATA");
	public static final StorageStatus NOT_MODIFIED = new StorageStatus(NOT_MODIFIED_CODE, StorageStatus.class.getName() + ".NOT_MODIFIED");
	public static final StorageStatus FORBIDDEN = new StorageStatus(FORBIDDEN_CODE, StorageStatus.class.getName() + ".FORBIDDEN");
	public static final StorageStatus TITLE_UNIQUE_CONSTRAINT_VIOLATION = new StorageStatus(TITLE_UNIQUE_CONSTRAINT_VIOLATION_CODE, StorageStatus.class.getName() + ".TITLE_UNIQUE_CONSTRAINT_VIOLATION");
	
	private final String resourceKey;
	private final Object params[];

	protected StorageStatus(int code, String resourceKey, Object...params) {
		super(code);
		this.resourceKey = resourceKey;
		this.params = params;
	}

	@Override
	public LocalizedStatus createLocalizedStatus(ResourceBundle resources) {
		String resource = resources.getString(resourceKey);
		if (params != null && params.length > 0) {
			resource = MessageFormat.format(resource, params);
		}
		return new LocalizedStatus(this, resource);
	}

	@Override
	public boolean isSuccess() {
		return code == 0;
	}
	
	public static StorageStatus notFound(Object id) {
		return new StorageStatus(ENTITY_NOT_FOUND_CODE, StorageStatus.class.getName()
				+ ".ENTITY_ID_NOT_FOUND", id.toString());
	}
	
	public static StorageStatus badRequestId(Object id) {
		return new StorageStatus(BAD_REQUEST_CODE,
				StorageStatus.class.getName() + ".BAD_REQUEST_ID", id);
	}
	
	public static StorageStatus permissionViolation(Object userId, Object targetId) {
		return new StorageStatus(FORBIDDEN_CODE,
				StorageStatus.class.getName() + ".FORBIDDEN_ID", userId, targetId);
	}
	
	public static StorageStatus permissionViolation() {
		return new StorageStatus(FORBIDDEN_CODE,
				StorageStatus.class.getName() + ".FORBIDDEN");
	}
	
}
