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
package ru.appliedtech.images.imagic;

import java.io.File;

import lombok.val;
import ru.appliedtech.application.AppException;
import ru.appliedtech.images.IImagesService;

public class MagicImagesService implements IImagesService {

	private final MagicImagesConfig config;

	public MagicImagesService(MagicImagesConfig config) {
		this.config = config;
	}
	
	@Override
	public File resize(File src, File dest, int type, int width, int height, boolean crop) throws AppException {
		
		if (dest.isDirectory()) {
			dest = new File(dest, src.getName());
		}
		
		val transformation = Transformations.resize()
				.setType(type)
				.setWidth(width)
				.setHeight(height)
				.setCrop(crop)
				.build();
		
		transformation.apply(config.executable, src, dest);
		
		return dest;
	}

}
