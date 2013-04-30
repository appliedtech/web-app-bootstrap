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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Basic abstract implementation of a storage record.
 * @param <P> The type of the unique key of the record
 * @author Igor Pavlenko
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractDescriptor<P extends Comparable<P>> implements Descriptor<P>
{
	/**
	 * Unique key of the record.
	 */
	private @Getter @Setter(AccessLevel.PROTECTED) P id;

	@Override
	public int compareTo(Descriptor<P> o)
	{
		return id.compareTo(o.getId());
	}

	@Override
	public int hashCode()
	{
		return id == null ? 0 : id.hashCode() ^ getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		return hashCode() == obj.hashCode() && obj instanceof Descriptor<?> && getClass().equals(obj.getClass())
				&& (id != null && id.equals(((Descriptor<?>) obj).getId()) || id == null && ((Descriptor<?>) obj).getId() == null);
	}
}
