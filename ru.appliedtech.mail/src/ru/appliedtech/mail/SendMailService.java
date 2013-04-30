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

import java.net.URL;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import ru.appliedtech.application.AC;
import ru.appliedtech.application.AppException;
import ru.appliedtech.application.ApplicationStatus;
import ru.appliedtech.application.Globals;
import ru.appliedtech.application.resources.IResourcesService;
import ru.appliedtech.template.ITemplatesService;

/**
 * Basic implementation of the {@link ISendMailService}.
 * @author Igor Pavlenko
 */
public class SendMailService implements ISendMailService {
	private static final String CONTENT_TYPE_HTML = "text/html; charset=UTF-8"; //$NON-NLS-1$

	private static final String HEADER_CONTENT_TYPE = "Content-Type"; //$NON-NLS-1$
	private static final String HEADER_CONTENT_ID = "Content-ID"; //$NON-NLS-1$

	private static final String CONTENT_ID_PATTERN = "<{0}>"; //$NON-NLS-1$
	private static final String MIME_SUBTYPE = "related"; //$NON-NLS-1$

	private final SendMailConfig config;

	/**
	 * Конструктор.
	 * @param config Конфиг данного сервиса.
	 */
	public SendMailService(SendMailConfig config) {
		this.config = config;
	}

	public MailTemplate getTemplate(String name) {
		return this.config.getTemplate(name);
	}

	@Override
	public void sendMessage(String email, MailTemplate template, Map<String, ?> params) throws AppException {
		ITemplatesService templates = AC.getInsecureService(ITemplatesService.class);
		IResourcesService resources = AC.getInsecureService(IResourcesService.class);

		try {
			MimeMessage message = new MimeMessage(config.session);
			message.addRecipient(RecipientType.TO, new InternetAddress(email));
			message.setSubject(template.subject, Globals.CHARSET_UTF8.name());

			message.setFrom(new InternetAddress(config.session.getProperty("mail.smtp.from")));
			message.setSentDate(new Date());

			MimeMultipart multipart = new MimeMultipart(MIME_SUBTYPE);
			BodyPart messageBodyPart = new MimeBodyPart();

			String content = templates.process(template.resource, params);
			messageBodyPart.setContent(content, CONTENT_TYPE_HTML);
			multipart.addBodyPart(messageBodyPart);

			// Add necessary attachments to the message
			for (MailAttachment attachment : template) {
				messageBodyPart = new MimeBodyPart();
				URL resourceURL = resources.getURL(attachment.resource);
				DataSource fds = new URLDataSource(resourceURL);
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader(HEADER_CONTENT_ID, MessageFormat.format(CONTENT_ID_PATTERN, attachment.name));
				messageBodyPart.setHeader(HEADER_CONTENT_TYPE, attachment.type);
				messageBodyPart.setFileName(attachment.name);
				messageBodyPart.setDisposition(attachment.disposition);
				multipart.addBodyPart(messageBodyPart);
			}
			message.setContent(multipart);
			Transport.send(message);
		} catch (AddressException e) {
			throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		} catch (MessagingException e) {
			throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		}
	}
}
