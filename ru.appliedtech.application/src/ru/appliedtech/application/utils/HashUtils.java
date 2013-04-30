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

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.appliedtech.application.AppException;
import ru.appliedtech.application.ApplicationStatus;


/**
 * Helper class to get hashes.
 * @author Igor V. Pavlenko
 * @date 29.08.2009
 */
public abstract class HashUtils {
	private static final String ALGORITHM_MD5 = "MD5"; //$NON-NLS-1$
	private static final String ALGORITHM_SHA1 = "SHA-1"; //$NON-NLS-1$

	/**
	 * Gets a hash from the specified text in specified charset
	 * @param text The text
	 * @param charsetName The charset name
	 * @return The hash
	 * @throws AppException If the named charset is not supported algorithm is not supported
	 */
	public String hash(String text, String charsetName) throws AppException {
		try {
			return hash(text, Charset.forName(charsetName));
		} catch (IllegalArgumentException e) {
			throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		}
	}

	/**
	 * Gets a hash from the specified data
	 * @param data The data
	 * @return The hash
	 * @throws AppException If algorithm is not supported
	 */
	public abstract byte[] hash(byte[] data) throws AppException;

	/**
	 * Gets a hash in HEX from the specified text in specified charset
	 * @param text The text
	 * @param charset The character set
	 * @return The hash in HEX
	 * @throws AppException If the named charset is not supported or algorithm is not supported
	 */
	public abstract String hash(String text, Charset charset) throws AppException;
	
	/**
	 * Use java MessageDigest to create hash
	 * @param algorithmIdentifier
	 * 			hash algorithm identifier for MessageDigest
	 * @param data
	 * 			data to hash
	 * @return
	 * 			hash bytes
	 * @throws AppException
	 * 			in case when there is no 
	 */
	private static byte [] useJavaHashAlgorithm(String algorithmIdentifier, byte [] data) throws AppException
	{
		try {
			MessageDigest md = MessageDigest.getInstance(algorithmIdentifier);
			md.update(data, 0, data.length);
			byte[] hash = md.digest();
			return hash;
		} catch (NoSuchAlgorithmException e) {
			throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		}
	}

	/**
	 * The MD5 hash utils
	 */
	public static final HashUtils MD5 = new HashUtils() {

		@Override
		public byte[] hash(byte[] data) throws AppException {
			return useJavaHashAlgorithm(ALGORITHM_MD5, data);
		}

		@Override
		public String hash(String text, Charset charset) throws AppException {
			return ConversionUtils.HEX.encode(hash(text.getBytes(charset)));
		}
	};
	
	/**
	 * The SHA-1 hash utils
	 */
	public static final HashUtils SHA1 = new HashUtils() {

		@Override
		public byte[] hash(byte[] data) throws AppException {
			return useJavaHashAlgorithm(ALGORITHM_SHA1, data);
		}

		@Override
		public String hash(String text, Charset charset) throws AppException {
			return ConversionUtils.HEX.encode(hash(text.getBytes(charset)));
		}
	};
}
