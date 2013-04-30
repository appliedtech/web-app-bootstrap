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

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import lombok.Cleanup;
import lombok.val;

public class FileUtils
{
    private final static int BUFFER_SIZE = 256 * 1024;

    private static final Logger LOG = Logger.getLogger(FileUtils.class.getName());

	public static File getTmpFile() {
		try {
			val file = File.createTempFile("iap", ".tmp");
			file.deleteOnExit();
			return file;
		} catch (IOException e) {
		}
		return null;
	}

    
    /**
     * @param source
     *            is stream to copy from
     * @param target
     *            is stream to copy to.
     * @param bClose
     *            close both streams at the end of copying (successful or not)
     *            or not.
     * @return Number of bytes copied
     * @throws IOException
     *             if error occurred
     */    
	public static final int copyStream(InputStream source, OutputStream target,
			boolean bClose) throws IOException {
		int processed = 0;
		try {
			val buffer = new byte[BUFFER_SIZE];
			while (true) {
				int read = source.read(buffer);
				if (read > 0) {
					processed += read;
					target.write(buffer, 0, read);
				} else if (read < 0) {
					break;
				}
			}
			target.flush();
		} finally {
			if (bClose) {
				closeClear(source);
				closeClear(target);
			}
		}
		return processed;
	}

    /**
     * Closes input stream if it is not null, ignores exceptions
     * 
     * @param is
     */
	public static void closeClear(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ignored) {
			LOG.log(Level.WARNING, ignored.getMessage(), ignored);
		}
	}
    
    /**
     * Returns true if all deletions were successful.
     * If any deletion fails, the method stops attempting to delete and returns false.
     * 
     * @param dir The directory to delete 
     * @return True if all deletions were successful
     */
	public static boolean deleteDirRecursively(File dir) {
		if (dir.isDirectory()) {
			val children = dir.list();
			if (children != null) {
				for (String child : children) {
					if (!deleteDirRecursively(new File(dir, child))) {
						return false;
					}
				}
			}
		}

		return dir.delete();
	}
    
    /**
     * Unzip inputStream archive. Closes input stream.
     * @param inputStream
     * @param dstDirectory
     * @throws IOException 
     */
	public static void unzip(InputStream inputStream, File dstDirectory)
			throws IOException {
		ZipInputStream zipInputStream = null;
		try {
			zipInputStream = new ZipInputStream(inputStream);
			ZipEntry entry = null;
			while ((entry = zipInputStream.getNextEntry()) != null) {
				val entryFile = new File(dstDirectory, entry.getName());
				if (entry.isDirectory()) {
					entryFile.mkdirs();
					continue;
				}

				val parentFile = entryFile.getParentFile();
				if (parentFile != null) {
					parentFile.mkdirs();
				}

				@Cleanup
				val fileOutputStream = new FileOutputStream(entryFile);
				@Cleanup
				val targetStream = new BufferedOutputStream(fileOutputStream);
				copyStream(zipInputStream, targetStream, false);
			}
		} finally {
			closeClear(zipInputStream);
			closeClear(inputStream);
		}
	}

    /**
     * @param file
     * @param encoding code page of file.
     * @return String with file content
     * @throws IOException - if an I/O error occurs.
     */
	public static byte[] loadFile(File file) throws IOException {
		val is = new FileInputStream(file);
		val os = new ByteArrayOutputStream();
		copyStream(is, os, true);
		return os.toByteArray();
	}

}