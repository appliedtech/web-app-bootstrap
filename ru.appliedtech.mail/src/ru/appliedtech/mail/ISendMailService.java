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
package ru.appliedtech.mail;

import java.util.Map;

import org.omg.CORBA.portable.ApplicationException;

import ru.appliedtech.application.AppException;
import ru.appliedtech.application.services.IService;

/**
 * Base interface for mailer service.
 * @author Igor Pavlenko
 */
public interface ISendMailService extends IService
{
	/**
	 * Builds message from the template and Sends it to the given address.
	 * @param email The address.
	 * @param subject The Subject.
	 * @param template The template.
	 * @param params template parameters.
	 * @throws ApplicationException
	 */
	public void sendMessage(String email, MailTemplate template, Map<String, ?> params) throws AppException;
}
