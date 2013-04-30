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
package ru.appliedtech.application.lang;

/**
 * The pair of objects (for coding in functional style)
 * @author Igor Pavlenko
 * @param <T>
 * @param <P>
 */
public class Tupple2<T, P>
{
	/**
	 * The first object.
	 */
	public final T a;
	
	/**
	 * The second object.
	 */
	public final P b;
	
	/**
	 * Constructor.
	 * @param a The first object
	 * @param b The first object
	 */
	public Tupple2(T a, P b)
	{
		this.a = a;
		this.b = b;
	}
	
	@Override
	public int hashCode()
	{
		return (a != null ? a.hashCode() : 0) ^ (b != null ? b.hashCode() : 0);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Tupple2<?, ?>)
		{
			Tupple2<?, ?> that = (Tupple2<?, ?>) obj;
			return hashCode() == obj.hashCode()
				&& (a == that.a || a != null && a.equals(that.a))
				&& (b == that.b || b != null && b.equals(that.b));
		}
		return false;
	}
}
