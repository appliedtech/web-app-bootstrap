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

import java.util.Collection;

import ru.appliedtech.application.lang.ITransformer;
import ru.appliedtech.application.registry.IPredicate;


/**
 * Helper for coding in functional style.
 * @author Igor Pavlenko
 */
public class CollectionUtils
{
	/**
	 * Filters data in the collection. 
	 * @param src
	 * @param dest
	 * @param predicate
	 */
	public static <P> void select(Collection<? extends P> src, Collection<? super P> dest, IPredicate<P> predicate)
	{
		for (P element : src)
		{
			if (predicate.truly(element))
			{
				dest.add(element);
			}
		}
	}

	/**
	 * Transforms data in the collection.
	 * @param src
	 * @param dest
	 * @param transformer
	 */
	public static <P, Q> void map(Collection<? extends P> src, Collection<? super Q> dest, ITransformer<P, Q> transformer)
	{
		for (P element : src)
		{
			Q result = transformer.transform(element);
			if (result != null)
			{
				dest.add(result);
			}
		}
	}
}
