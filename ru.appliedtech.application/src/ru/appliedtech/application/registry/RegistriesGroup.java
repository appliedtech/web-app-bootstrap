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

import java.util.HashMap;
import java.util.Map;

/**
 * Any {@link Registry} can be registered in some "context", for example
 * "Application Context" or "User Context". When we dispose context we want to
 * clear all the cache and remove all registries. This class is an
 * implementation of the context.
 * 
 * @author Igor Pavlenko
 */
public class RegistriesGroup
{
	private final Map<Object, Registry<?, ?, ?>> registries = new HashMap<Object, Registry<?, ?, ?>>();

	/**
	 * Stores registry in the context.
	 * @param key The unique key of the registry.
	 * @param registry The registry.
	 */
	public <Q extends Element<?, ?>> void put(Class<Q> key, Registry<?, ?, Q> registry)
	{
		registries.put(key, registry);
	}

	/**
	 * Gets the registry from this context.
	 * @param <P> The type of the unique element's key (element is a record in the registry)
	 * @param <T> The type of the descriptor of the element (contains all element's data fields).
	 * @param <Q> The type of the element.
	 * @param key The key of the registry.
	 * @return The registry.
	 */
	@SuppressWarnings("unchecked")
	public <P extends Comparable<P>, T extends Descriptor<P>, Q extends Element<P, T>> Registry<P, T, Q> get(Class<Q> key)
	{
		return (Registry<P, T, Q>) registries.get(key);
	}

	/**
	 * Removes the {@link Registry} from this context.
	 * @param key The key of the registry
	 */
	public void remove(Object key)
	{
		registries.remove(key);
	}
}
