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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import ru.appliedtech.application.AppException;
import ru.appliedtech.application.ApplicationStatus;


/**
 * Hash Utils for HMAC hash types
 * @author bstepanov
 *
 */
public abstract class HMacHashUtils {
	private static final String ALGORITHM_HMAC_SHA1 = "HmacSHA1"; //$NON-NLS-1$

	/**
	 * Gets a hash from the specified text in specified charset
	 * @param text The text
	 * @param secret The signing secret key
	 * @param charsetName The charset name
	 * @return The hash
	 * @throws AppException If the named charset is not supported algorithm is not supported
	 */
	public String hash(String text, String secret, String charsetName) throws AppException {
		try {
			return hash(text, secret, Charset.forName(charsetName));
		} catch (IllegalArgumentException e) {
			throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		}
	}

	/**
	 * Gets a hash from the specified data
	 * @param data The data
	 * @param secret The signing secret key
	 * @return The hash
	 * @throws AppException If algorithm is not supported
	 */
	public abstract byte[] hash(byte[] data, byte[] secret) throws AppException;

	/**
	 * Gets a hash in HEX from the specified text in specified charset
	 * @param text The text
	 * @param secret The signing secret key
	 * @param charset The character set
	 * @return The hash in HEX
	 * @throws AppException If the named charset is not supported or algorithm is not supported
	 */
	public abstract String hash(String text, String secret, Charset charset) throws AppException;
	
	/**
	 * Use java MessageDigest to create hash
	 * @param algorithm
	 * 			hash algorithm identifier for MessageDigest
	 * @param data
	 * 			data to hash
	 * @param secret
	 * 			signing secret key
	 * @return
	 * 			hash bytes
	 * @throws AppException
	 * 			in case when there is no 
	 */
	private static byte [] useJavaHMacHashAlgorithm(String algorithm, byte [] data, byte[] secret) throws AppException
	{     
        try {
			Mac mac = Mac.getInstance(algorithm);
			mac.init(new SecretKeySpec(secret, algorithm));
			return mac.doFinal(data);
		} catch (InvalidKeyException e) {
			throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		} catch (NoSuchAlgorithmException e) {
			throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		} catch (IllegalStateException e) {
			throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		}
	}

	
	/**
	 * The SHA-1 hash utils
	 */
	public static final HMacHashUtils HMAC_SHA1 = new HMacHashUtils() {

		@Override
		public byte[] hash(byte[] data, byte[] secret) throws AppException {
			return useJavaHMacHashAlgorithm(ALGORITHM_HMAC_SHA1, data, secret);
		}

		@Override
		public String hash(String text, String secret, Charset charset) throws AppException {
			return ConversionUtils.HEX.encode(hash(text.getBytes(charset), secret.getBytes(charset)));
		}
	};
}
