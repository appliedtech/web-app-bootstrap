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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import lombok.Cleanup;
import lombok.val;
import ru.appliedtech.application.AppException;
import ru.appliedtech.application.ApplicationStatus;
import ru.appliedtech.application.utils.FileUtils;
import ru.appliedtech.images.IImagesService;

public abstract class Transformations {
	
	private static boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");

	public static class Transformation {
		
		private final String params;

		private Transformation(String params) {
			this.params = params;
		}
		
		public void apply(String executable, File src, File dest) throws AppException {
			String formatString = isWindows ? "\"{0}\" \"{1}\" {2} \"{3}\"" : 
				"{0} {1} {2} {3}";
			
			val command = MessageFormat.format(formatString,
					executable,
					src.getAbsoluteFile(),
					params,
					dest.getAbsoluteFile()
			);
			try {
				val process = Runtime.getRuntime().exec(command);
				int status = process.waitFor();
				if (status != 0) {
					@Cleanup val input = process.getErrorStream();
					val output = new ByteArrayOutputStream(128);
					FileUtils.copyStream(input, output, false);
					String message = new String(output.toByteArray());
					throw new AppException(message, ApplicationStatus.FAILURE_UNKNOWN_REASON);
				}
			} catch (IOException | InterruptedException e) {
				throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			}
		}
	}
	
	public abstract static class TransformationBuilder {
		public abstract Transformation build() throws AppException;
	}
	
	public static class ResizeTransformationBuilder extends TransformationBuilder {

		private int type;
		private int width;
		private int height;
		private boolean crop;
		
		public ResizeTransformationBuilder setType(int type) { this.type = type; return this; }
		public ResizeTransformationBuilder setWidth(int width) { this.width = width; return this; }
		public ResizeTransformationBuilder setHeight(int height) { this.height = height; return this; }
		public ResizeTransformationBuilder setCrop(boolean crop) { this.crop = crop; return this; }
		
		@Override
		public Transformation build() throws AppException {
			val builder = new StringBuilder();
			builder
				.append(isWindows ? "-resize \"" : "-resize ")
				.append(width)
				.append('x')
				.append(height);
			switch (type) {
				case IImagesService.RESIZE_IGNORE:
					builder.append("!");
					break;
				case IImagesService.RESIZE_SHRINK:
					builder.append(">");
					break;
				case IImagesService.RESIZE_ENLARGE:
					builder.append("<");
					break;
				case IImagesService.RESIZE_FILL:
					builder.append("^");
					break;
			}
			if (isWindows) {
				builder.append("\"");
			}

			if (crop) {
				builder.append(" -gravity center -crop ")
					.append(width)
					.append('x')
					.append(height)
					.append("+0+0 +repage");
			}

			return new Transformation(builder.toString());
		}
	}
	
	public static ResizeTransformationBuilder resize() {
		return new ResizeTransformationBuilder();
	}
}
