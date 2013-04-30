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
package ru.appliedtech.application.resources;

import java.net.URL;

import ru.appliedtech.application.AppException;
import ru.appliedtech.application.ApplicationStatus;

public class ResourcesService implements IResourcesService {
	
	private static final String MESSAGE_BUNDLE_DOES_NOT_EXIST = "Bundle does not exist";
	private static final String MESSAGE_INVALID_RESOURCE_NAME = "Invalid resource name";

	private static final String BUNDLE_SEPARATOR = "://";

	private final ResourcesConfig config;

	public ResourcesService(ResourcesConfig config) {
		this.config = config;
	}

	@Override
	public URL getURL(String bundle, String spec) throws AppException {
		BundleConfig bConfig = config.getBundle(bundle);
		if (bConfig != null) {
			return bConfig.resolver.getURL(spec);
		}
		throw new AppException(MESSAGE_BUNDLE_DOES_NOT_EXIST, ApplicationStatus.FAILURE_UNKNOWN_REASON);
	}

	@Override
	public URL getURL(String name) throws AppException {
		int i;
		if (name != null && (i = name.indexOf(BUNDLE_SEPARATOR)) > 0) {
			String bundle = name.substring(0, i);
			String spec = name.substring(i + BUNDLE_SEPARATOR.length());

			return getURL(bundle, spec);
		}
		throw new AppException(MESSAGE_INVALID_RESOURCE_NAME, ApplicationStatus.FAILURE_UNKNOWN_REASON);
	}
}
