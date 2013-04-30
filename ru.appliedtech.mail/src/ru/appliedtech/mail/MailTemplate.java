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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Named template of the mail message.
 * @author Igor Pavlenko
 */
public class MailTemplate implements Iterable<MailAttachment>
{
	/**
	 * The name of the template.
	 */
	public final String name;
	
	/**
	 * The default Subject
	 */
	public final String subject;
	
	/**
	 * The name of a template's content resource.
	 */
	public final String resource;
	
	private List<MailAttachment> attachments = new LinkedList<MailAttachment>();
	
	/**
	 * Constructor.
	 * @param name The name of the template.
	 * @param subject The default Subject.
	 * @param resource The name of a template's content resource.
	 */
	public MailTemplate(String name, String subject, String resource)
	{
		this.name = name;
		this.subject = subject;
		this.resource = resource;
	}
	
	/**
	 * Adds the attachment.
	 * @param attachment The attachment.
	 */
	public void addAttachment(MailAttachment attachment)
	{
		attachments.add(attachment);
	}

	@Override
	public Iterator<MailAttachment> iterator()
	{
		return attachments.iterator();
	}
}
