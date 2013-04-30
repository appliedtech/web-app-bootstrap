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
package ru.appliedtech.template.velocity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import ru.appliedtech.application.AC;
import ru.appliedtech.application.AppException;
import ru.appliedtech.application.resources.IResourcesService;

public class VelocityResourceLoader extends ResourceLoader {
	@Override
	public void init(ExtendedProperties configuration) {
		// do nothing
	}

	@Override
	public InputStream getResourceStream(String name)
			throws ResourceNotFoundException {
		IResourcesService resources = AC.getInsecureService(IResourcesService.class);
		try {
			URLConnection connection = resources.getURL(name).openConnection();
			return connection.getInputStream();
		} catch (AppException | IOException e) {
			throw new ResourceNotFoundException(e);
		}
	}

	@Override
	public boolean isSourceModified(Resource resource) {
		long fileLastModified = getLastModified(resource);
		// if the file is unreachable or otherwise changed
		if (fileLastModified == 0
				|| fileLastModified != resource.getLastModified()) {
			return true;
		}
		return false;
	}

	@Override
	public long getLastModified(Resource resource) {
		IResourcesService resources = AC.getInsecureService(IResourcesService.class);
		try {
			URLConnection connection = resources.getURL(resource.getName())
					.openConnection();
			return connection.getLastModified();
		} catch (AppException | IOException e) {
			throw new ResourceNotFoundException(e);
		}
	}
}
