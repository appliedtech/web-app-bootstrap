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
package ru.appliedtech.application.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {@link HttpContext} is a {@link ThreadLocal} storage for http-request. Used together with {@link HttpContextFilter}.
 * @author Igor Pavlenko
 */
public class HttpContext
{
	private final static ThreadLocal<HttpContext> contextThreadLocal = new ThreadLocal<HttpContext>();
	
	private static final String SESSION_ATTRIBUTE_SESSION_DATA = "session-data";
	//private static final String SESSION_ATTRIBUTE_COOKIES_DATA = "cookies-data";

	static void build(HttpServletRequest request, HttpServletResponse response)
	{
		contextThreadLocal.set(new HttpContext(request, response));
	}
	
	static void destroy()
	{
		contextThreadLocal.remove();
	}

	/**
	 * Gets the context from the {@link ThreadLocal}. Should be called from the the request's thread.
	 * @return  The context.
	 */
	public static HttpContext get()
	{
		return contextThreadLocal.get();
	}

	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private HttpContext(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
	}
	
	/**
	 * Gets {@link HttpServletRequest}.
	 * @return The request.
	 */
	public HttpServletRequest getRequest()
	{
		return request;
	}
	
	/**
	 * Gets {@link HttpServletResponse}.
	 * @return The response.
	 */
	public HttpServletResponse getResponse()
	{
		return response;
	}
	
	/**
	 * Gets session storage.
	 * @return The session storage. Wraps the {@link HttpSession}.
	 */
	public SessionData getSessionData()
	{
		HttpSession session = request.getSession();
		Object object = session.getAttribute(SESSION_ATTRIBUTE_SESSION_DATA);
		if (object instanceof SessionData)
		{
			return (SessionData) object;
		}
		else
		{
			SessionData data = new SessionData();
			session.setAttribute(SESSION_ATTRIBUTE_SESSION_DATA, data);
			return data;
		}
	}
	
	/**
	 * Gets the locale from the http request.
	 * @return The locale
	 */
	public Locale getLocale()
	{
		return request.getLocale();
	}
}
