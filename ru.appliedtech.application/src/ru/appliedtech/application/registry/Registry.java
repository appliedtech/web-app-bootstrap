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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import lombok.val;

/**
 * Can be used to create "memory table" from the set of {@link Element}s.
 * @param <P> The type of the unique element's key (element is a record in the registry)
 * @param <T> The type of the descriptor of the element (contains all element's data fields).
 * @param <Q> The type of the element.
 * @author Igor Pavlenko
 */
public abstract class Registry<P extends Comparable<P>, T extends Descriptor<P>, Q extends Element<P, T>> extends
		Observable implements Iterable<Q>, Observer
{
	private final static RegistriesGroup DEFAULT_GROUP = new RegistriesGroup();

	private Map<P, Q> m_elements = new TreeMap<P, Q>();
	
	/**
	 * Constructor. Creates registry in the {@link Registry#DEFAULT_GROUP} context.
	 * @param key Unique ID of the registry.
	 */
	protected Registry(Class<Q> key)
	{
		this(key, DEFAULT_GROUP);
	}

	/**
	 * Constructor. Creates registry in the specified context.
	 * @param key Unique ID of the registry.
	 * @param group The context.
	 */
	protected Registry(Class<Q> key, RegistriesGroup group)
	{
		group.put(key, this);
	}
	
	/**
	 * Empty constructor (it does nothing).
	 */
	protected Registry()
	{
		// empty
	}

	/**
	 * Gets the element by unique ID of the descriptor.
	 * @param descriptor The descriptor
	 * @return The element or <code>null</code>.
	 */
	public Q getElement(T descriptor)
	{
		return m_elements.get(descriptor.getId());
	}

	/**
	 * Gets the descriptor by unique ID.
	 * @param id The ID
	 * @return The descriptor or <code>null</code>.
	 */
	public T get(P id)
	{
		Q element = m_elements.get(id);
		return element != null ? element.getDescriptor() : null;
	}

	/**
	 * Get the element by unique ID. 
	 * @param id The ID
	 * @return The element or <code>null</code>.
	 */
	public Q getElement(P id)
	{
		return m_elements.get(id);
	}

	/**
	 * Adds the element into the registry and notifies registry's observers (thread-safe).
	 * @param element The element.
	 */
	public synchronized void add(Q element)
	{
		T descriptor = element.getDescriptor();
		m_elements.put(descriptor.getId(), element);
		setChanged();
		notifyObservers(new Event(Event.CODE_ADDED, element));
	}

	/**
	 * Removes the element from the registry and notifies registry's observers (thread-safe).
	 * @param id The ID of the element.
	 * @return Removed element or <code>null</code>. 
	 */
	public synchronized Q remove(P id)
	{
		Q element = m_elements.remove(id);
		if (element != null)
		{
			setChanged();
			notifyObservers(new Event(Event.CODE_REMOVED, element));
		}
		return element;
	}
	
	/**
	 * Notifies registry's observers (thread-safe).
	 * @param descriptor The descriptor of changed element.
	 */
	public synchronized void notifyUpdated(T descriptor)
	{
		Q element = m_elements.get(descriptor.getId());
		if (element != null)
		{
			setChanged();
			notifyObservers(new Event(Event.CODE_UPDATED, element));
		}
	}
	
	/**
	 * Notifies registry's observers (thread-safe).
	 * @param element Changed element.
	 */
	public synchronized void notifyUpdated(Q element)
	{
		setChanged();
		notifyObservers(new Event(Event.CODE_UPDATED, element));
	}

	/**
	 * Clears registry and notifies registry's observers (thread-safe). 
	 */
	public synchronized void clear()
	{
		Collection<Q> elements = m_elements.values();
		m_elements = new TreeMap<P, Q>();
		setChanged();
		notifyObservers(new Event(Event.CODE_REMOVED, elements));
	}

	/**
	 * Fills the collection with data from this registry (thread-safe).
	 * @param collection The collection to fill.
	 * @return The first method argument.
	 */
	public synchronized Collection<Q> fillWithElements(Collection<Q> collection)
	{
		collection.addAll(m_elements.values());
		return collection;
	}
	
	/**
	 * Fills the collection with filtered data from this registry  (thread-safe). Predecate used to filter data.
	 * @param collection The collection to fill.
	 * @param condition The selection condition.
	 * @return The firrst method argument.
	 */
	public synchronized Collection<Q> fillWithElements(Collection<Q> collection, IPredicate<Q> condition)
	{
		for (Q element : m_elements.values())
		{
			if (condition.truly(element))
			{
				collection.add(element);
			}
		}
		return collection;
	}
	
	/**
	 * Gets a collection of registry elements (thread-safe)
	 * @return Collection of elements.
	 */
	public synchronized Collection<Q> getElements()
	{
		return new LinkedList<Q>(m_elements.values());
	}

	/**
	 * Gets the number of elements in the registry (thread-safe).
	 * The number of elements in registry.
	 */
	public synchronized int size()
	{
		return m_elements.size();
	}

	/**
	 * Gets the registry from the default context.
	 * @param <P> The type of the unique element's key (element is a record in the registry)
	 * @param <T> The type of the descriptor of the element (contains all element's data fields).
	 * @param <Q> The type of the element.
	 * @param key The key of the registry in the context.
	 * @param group The context.
	 * @return The registry or <code>null</code>.
	 */
	public static <P extends Comparable<P>, T extends Descriptor<P>, Q extends Element<P, T>> Registry<P, T, Q> getRegistry(
			Class<Q> key)
	{
		return DEFAULT_GROUP.get(key);
	}

	/**
	 * Gets the registry from the context.
	 * @param <P> The type of the unique element's key (element is a record in the registry)
	 * @param <T> The type of the descriptor of the element (contains all element's data fields).
	 * @param <Q> The type of the element.
	 * @param key The key of the registry in the context.
	 * @param group The context.
	 * @return The registry or <code>null</code>.
	 */
	public static <P extends Comparable<P>, T extends Descriptor<P>, Q extends Element<P, T>> Registry<P, T, Q> getRegistry(
			Class<Q> key, RegistriesGroup group)
	{
		return group.get(key);
	}
	
	/**
	 * Removes {@link Registry} from the specified context.
	 * @param key The key of the registry.
	 * @param group Группа The context.
	 */
	public static void removeRegistry(Object key, RegistriesGroup group)
	{
		group.remove(key);
	}

	/**
	 * Gets the lock object for thread-safe operations.
	 * @return The lock object.
	 */
	public Object getLock()
	{
		return this;
	}
	
	@Override
	public Iterator<Q> iterator()
	{
		return m_elements.values().iterator();
	}
	
	public synchronized Map<P, Q> replaceElementsSync() {
		val elements = new TreeMap<P, Q>();
		elements.putAll(m_elements);
		val old = m_elements;
		m_elements = elements;
		return old;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// Do notthing by default
	}
}
