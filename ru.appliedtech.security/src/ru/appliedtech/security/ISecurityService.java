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

import java.nio.charset.Charset;

import org.omg.CORBA.portable.ApplicationException;

import ru.appliedtech.application.services.IService;

/**
 * Encryption, decryption, validations, etc.
 * @author Igor Pavlenko
 */
public interface ISecurityService extends IService
{
	/**
	 * Encrypts user data for storage. 
	 * @param data Data to encrypt.
	 * @return Encrypted data.
	 * @throws ApplicationException
	 */
	public byte[] encryptUserData(byte[] data) throws SecurityException;
	
	/**
	 * Decrypts user data from storage. 
	 * @param data Data to decrypt.
	 * @return Decrypted data
	 * @throws ApplicationException
	 */
	public byte[] decryptUserData(byte[] data) throws SecurityException;

	/**
	 * Encrypts user data for communication purposes. 
	 * @param data Data to encrypt.
	 * @return Encrypted data.
	 * @throws ApplicationException При невозможности выполнить шифрацию.
	 */
	public byte[] encryptMessagingData(byte[] data) throws SecurityException;

	/**
	 * Decrypts user data used for communication purposes. 
	 * @param data Data to decrypt.
	 * @return Decrypted data
	 * @throws ApplicationException
	 */
	public byte[] decryptMessagingData(byte[] data) throws SecurityException;
	

	/**
	 * Hashes password for storage.
	 * @param password The password.
	 * @param charset Charset of the password.
	 * @return The hash. Cannot be <code>null</code>.
	 * @throws SecurityException 
	 */
	public byte[] hashPassword(String password, Charset charset) throws SecurityException;
	
	/**
	 * Checks the validity of user password (not the equality, but the validity of the format)
	 * @param password The password
	 * @throws SecurityException If the password is not valid
	 */
	public void checkPassword(String password) throws SecurityException;
	
	/**
	 * Checks the validity of user login (not the equality, but the validity of the format)
	 * @param login The login
	 * @throws SecurityException If the login is not valid.
	 */
	public void checkLogin(String login) throws SecurityException;
	
	/**
	 * Checks the equality of the password and password confirmation.
	 * @param password1 The password.
	 * @param password2 The confirmation.
	 * @throws SecurityException
	 */
	public void checkPasswordConfirmation(String password1, String password2) throws SecurityException;
}
