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
package ru.appliedtech.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import ru.appliedtech.application.services.ISecureService;
import ru.appliedtech.application.services.IService;


/**
 * Main application singleton.
 * @author Igor Pavlenko
 */
public class AC
{
	private static final AC INSTANCE = new AC();
	
	/**
	 * Gets a singleton instance of this class.
	 * @return The singleton instance of this class.
	 */
	public static AC getInstance()
	{
		return INSTANCE;
	}
	
	/**
	 * Exception with this message will be thrown if try to get unknown service
	 * from this class.
	 */
	private static final String MESSAGE_INVALID_NATURE = "Invalid nature."; //$NON-NLS-1$

	private final Map<String, IService> services = new HashMap<String, IService>();
	private final Map<String, IService> insecureServices = new HashMap<String, IService>();

	/**
	 * Adds service to a list of known services of this singleton. If the
	 * service class derrived from the {@link ISecureService} it will be
	 * possible to get the instance of the service through
	 * {@link AC#getService(Class)} call. Otherwise throug the
	 * {@link AC#getInsecureService(Class)}.
	 * 
	 * @param <T> Service super-interface.
	 * @param clazz The super-interface of the service (used as a key in the service container).
	 * @param service The service.
	 * @return <tt>this</tt> object (for method chains).
	 */
	public final <T extends IService> AC registerService(Class<? extends IService> clazz, T service)
	{
		if (ISecureService.class.isInstance(service))
		{
			services.put(clazz.getName(), service);
		}
		else
		{
			insecureServices.put(clazz.getName(), service);
		}
		return this;
	}
	
	/**
	 * Removes service from a list of known services of this singleton.
	 * @param clazz The super-interface of the service.
	 * @return <tt>this</tt> object (for method chains).
	 */
	public final <T extends IService> AC unregisterService(Class<? extends IService> clazz)
	{
		if (ISecureService.class.isAssignableFrom(clazz))
		{
			services.remove(clazz.getName());
		}
		else
		{
			insecureServices.remove(clazz.getName());
		}
		return this;
	}
	
	/**
	 * Removes all inclusions of the service in this singleton.
	 * @param service service instance.
	 * @return <tt>this</tt> object (for method chains).
	 */
	public final <T extends IService> AC unregisterService(T service)
	{
		unregisterService(services, service);
		unregisterService(insecureServices, service);
		return this;
	}
	
	// Unregister service from map
	private <T extends IService> void unregisterService(Map<String, IService> registeredServices, T service)
	{
		Collection<String> names = new HashSet<>();
		for (Map.Entry<String, IService> entry : registeredServices.entrySet())
		{
			if (entry.getValue() == service)
			{
				names.add(entry.getKey());
			}
		}
		for (String n : names)
		{
			registeredServices.remove(n);
		}
	}
	
	/**
	 * Gets a collection of unique registered services.
	 * @return Returns a services collection.
	 */
	public final Collection<IService> getAllUniqueServices()
	{
		Collection<IService> uniqueServices = new HashSet<>();
		uniqueServices.addAll(services.values());
		uniqueServices.addAll(insecureServices.values());
		return uniqueServices;
	}

	/**
	 * Gets secure service (derrived from {@link ISecureService}) by key (super-interface of the service).
	 * 
	 * @param <T> The super-interface of the service.
	 * @param clazz The super-interface of the service.
	 * @return The instance of the service. Never <code>null</code>.
	 * @throws AppRuntimeException If there is no such service.
	 */
	public static final <T extends IService> T getService(Class<T> clazz)
			throws AppRuntimeException
	{
		IService service = INSTANCE.services.get(clazz.getName());
		if (clazz.isInstance(service))
		{
			return clazz.cast(service);
		}
		throw new AppRuntimeException(MESSAGE_INVALID_NATURE);
	}
	
	/**
	 * Gets insecure service (derrived from {@link ISecureService}) by key (super-interface of the service). 
	 * 
	 * @param <T> The super-interface of the service.
	 * @param clazz The super-interface of the service.
	 * @return The instance of the service. Never <code>null</code>.
	 * @throws AppRuntimeException If there is no such service.
	 */
	public static final <T extends IService> T getInsecureService(Class<T> clazz) {
		IService service = INSTANCE.insecureServices.get(clazz.getName());
		if (clazz.isInstance(service))
		{
			return clazz.cast(service);
		}
		throw new AppRuntimeException(MESSAGE_INVALID_NATURE);
	}
	
}
