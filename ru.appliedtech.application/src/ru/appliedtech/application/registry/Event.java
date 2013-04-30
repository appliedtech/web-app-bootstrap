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
package ru.appliedtech.application.registry;

/**
 * The event in the {@link Registry}.
 * 
 * @author Igor Pavlenko
 */
public class Event
{
	/**
	 * When the element has been added into the registry. 
	 */
	public static final int CODE_ADDED = 1;

	/**
	 * When the element has been removed from the registry.
	 */
	public static final int CODE_REMOVED = 2;
	
	/**
	 * When the element has been updated in the registry.
	 */
	public static final int CODE_UPDATED = 3;

	/**
	 * Unique event code.
	 */
	public final int code;

	/**
	 * The data field of the event.
	 */
	public final Object element;
	
	/**
	 * The data field of the event (in the case when several updates occured)
	 */
	public final Object[] elements;

	/**
	 * Constructor
	 * @param code Event code.
	 * @param element Changed element.
	 */
	public Event(int code, Object element)
	{
		this.code = code;
		this.element = element;
		this.elements = null;
	}
}
