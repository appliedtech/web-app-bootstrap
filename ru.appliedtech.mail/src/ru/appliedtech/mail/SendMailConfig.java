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
import java.util.TreeMap;

import javax.mail.Session;

/**
 * The config of the {@link SendMailService}.
 * @author Igor Pavlenko
 */
public class SendMailConfig
{
	/**
	 * The mail session.
	 */
	public final Session session;
	
	private final Map<String, MailTemplate> templates = new TreeMap<>();
	
	/**
	 * Constructor.
	 * @param session The mail session.
	 */
	public SendMailConfig(Session session)
	{
		this.session = session;
	}
	
	/**
	 * Adds named template.
	 * @param template The template.
	 */
	public void addTemplate(MailTemplate template)
	{
		templates.put(template.name, template);
	}
	
	/**
	 * Gets named template by name.
	 * @param name The name.
	 * @return The template or <code>null</code>.
	 */
	public MailTemplate getTemplate(String name)
	{
		return templates.get(name);
	}
}
