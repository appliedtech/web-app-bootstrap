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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ru.appliedtech.application.AppException;
import ru.appliedtech.application.ApplicationStatus;


/**
 * Helper class used to encrypt and decrypt data
 * @author Igor V. Pavlenko
 * @date 29.08.2009
 */
public abstract class CryptoUtils {
	private static final String MESSAGE_CANNOT_CREATE_CIPHER_OBJECT = "Catton create Cipher object for algorithm: {0}"; //$NON-NLS-1$
	private static final String MESSAGE_INVALID_KEY = "Invalid key for algorithm: {0}"; //$NON-NLS-1$
	private static final String MESSAGE_ENCRYPTION_FAILED = "Encryption failed for algorithm: {0}"; //$NON-NLS-1$
	private static final String MESSAGE_DECRYPTION_FAILED = "Decryption failed for algorithm: {0}"; //$NON-NLS-1$;

	/**
	 * AES helper class
	 */
	public static class AESCryptoUtils extends CryptoUtils {
		private static final String NAME = "AES"; //$NON-NLS-1$

		private int m_length;

		/**
		 * �����������.
		 * @param length ����� AES-����� � �����.
		 */
		public AESCryptoUtils(int length) {
			m_length = length;
		}

		@Override
		public byte[] encrypt(byte[] key, byte[] data) throws AppException {
			try {
				SecretKeySpec skeySpec = new SecretKeySpec(key, NAME);
				Cipher cipher = Cipher.getInstance(NAME);
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
				return cipher.doFinal(data);
			} catch (NoSuchAlgorithmException e) {
				throw new AppException(MessageFormat.format(MESSAGE_CANNOT_CREATE_CIPHER_OBJECT, NAME), e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			} catch (NoSuchPaddingException e) {
				throw new AppException(MessageFormat.format(MESSAGE_CANNOT_CREATE_CIPHER_OBJECT, NAME), e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			} catch (InvalidKeyException e) {
				throw new AppException(MessageFormat.format(MESSAGE_INVALID_KEY, NAME), e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			} catch (IllegalBlockSizeException e) {
				throw new AppException(MessageFormat.format(MESSAGE_ENCRYPTION_FAILED, NAME), e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			} catch (BadPaddingException e) {
				throw new AppException(MessageFormat.format(MESSAGE_ENCRYPTION_FAILED, NAME), e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			}
		}

		@Override
		public byte[] decrypt(byte[] key, byte[] encrypted) throws AppException {
			try {
				SecretKeySpec skeySpec = new SecretKeySpec(key, NAME);
				Cipher cipher = Cipher.getInstance(NAME);
				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
				return cipher.doFinal(encrypted);
			} catch (NoSuchAlgorithmException e) {
				throw new AppException(MessageFormat.format(MESSAGE_CANNOT_CREATE_CIPHER_OBJECT, NAME), e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			} catch (NoSuchPaddingException e) {
				throw new AppException(MessageFormat.format(MESSAGE_CANNOT_CREATE_CIPHER_OBJECT, NAME), e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			} catch (InvalidKeyException e) {
				throw new AppException(MessageFormat.format(MESSAGE_INVALID_KEY, NAME), e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			} catch (IllegalBlockSizeException e) {
				throw new AppException(MessageFormat.format(MESSAGE_DECRYPTION_FAILED, NAME), e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			} catch (BadPaddingException e) {
				throw new AppException(MessageFormat.format(MESSAGE_DECRYPTION_FAILED, NAME), e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			}
		}

		@Override
		public byte[] generateKey() throws AppException {
			try {
				KeyGenerator kgen = KeyGenerator.getInstance(NAME);
				kgen.init(m_length);
				SecretKey skey = kgen.generateKey();
				return skey.getEncoded();
			} catch (NoSuchAlgorithmException e) {
				throw new AppException(MessageFormat.format(MESSAGE_CANNOT_CREATE_CIPHER_OBJECT, NAME), e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			}
		}
	}

	/**
	 * ��������/����������, ������������ 128-������ AES-����.
	 */
	public static CryptoUtils AES128 = new AESCryptoUtils(128);

	/**
	 * ��������/����������, ������������ 256-������ AES-����.
	 */
	public static CryptoUtils AES256 = new AESCryptoUtils(256);

	/**
	 * ��������/����������, ������������ ����������� XOR-�������� � ������.
	 */
	public static CryptoUtils XOR = new CryptoUtils() {
		@Override
		public byte[] encrypt(byte[] key, byte[] data) throws AppException {
			byte[] res = new byte[data.length];
			for (int i = 0; i < data.length; i++) {
				int shiftId = i % key.length;
				res[i] = (byte) (data[i] ^ key[shiftId]);
			}
			return res;
		}

		@Override
		public byte[] decrypt(byte[] key, byte[] encrypted) throws AppException {
			byte[] res = new byte[encrypted.length];
			for (int i = 0; i < encrypted.length; i++) {
				int shiftId = i % key.length;
				res[i] = (byte) (encrypted[i] ^ key[shiftId]);
			}
			return res;
		}

		@Override
		public byte[] generateKey() throws AppException {
			Random rand = new Random();
			int value = rand.nextInt();
			return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
		}
	};

	/**
	 * Encrypts data using specified key
	 * @param key Encryption/decryption key
	 * @param data Data to encrypt
	 * @return Encrypted data
	 * @throws AppException; If cannot encrypt
	 */
	public abstract byte[] encrypt(byte[] key, byte[] data) throws AppException;

	/**
	 * Decrypts encrypted data using specified key
	 * @param key Encryption/decryption key
	 * @param encrypted Encrypted data
	 * @return Data
	 * @throws AppException; If cannot decrypt
	 */
	public abstract byte[] decrypt(byte[] key, byte[] encrypted) throws AppException;

	/**
	 * Generates encryption key
	 * @return Generated key
	 * @throws AppException; If cannot generate key
	 */
	public abstract byte[] generateKey() throws AppException;

	/**
	 * Gets int value as byte array.
	 * @param value The integer value.
	 * @return Bytes of the value.
	 */
	public static byte[] toByteArray(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
	}

	/**
	 * Gets long value as byte array.
	 * @param value The long integer value.
	 * @return Bytes of the value.
	 */
	public static byte[] toByteArray(long value) {
		return new byte[] { (byte) (value >>> 56), (byte) (value >>> 48), (byte) (value >>> 40), (byte) (value >>> 32), (byte) (value >>> 24),
				(byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
	}

	/**
	 * ��������������� �� �������� ������ �������� ���� long.
	 * @param bytes ����� � ������� Long-����.
	 * @return ���������� ���� long.
	 */
	public static long longFromByteArray(byte[] bytes) {
		long value = 0;
		for (int i = 0; i < bytes.length; i++) {
			value = (value << 8) + (bytes[i] & 0xFF);
		}
		return value;
	}

	/**
	 * Gets int value of the specified bytes.
	 * @param bytes The bytes.
	 * @return The integer value.
	 */
	public static int fromByteArray(byte[] bytes) {
		return (bytes[0] << 24) + ((bytes[1] & 0xFF) << 16) + ((bytes[2] & 0xFF) << 8) + (bytes[3] & 0xFF);
	}
}
