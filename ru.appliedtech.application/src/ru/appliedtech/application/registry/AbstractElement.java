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

import java.text.Collator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Base abstract implementation of an object with storage backend. Such objects
 * have "state" (the set of fields in the descriptor field) and collectionm of "references"
 * (any object dependencies).
 * 
 * Inheritors may have been stored into the {@link Registry}. 
 * 
 * @param <P> The type of the unique object's key (the unique key of the object descriptor).
 * @param <T> The type of the descriptor.
 * @author Igor Pavlenko
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AbstractElement<P extends Comparable<P>, T extends Descriptor<P>> implements Element<P, T> {
	protected static final Collator COLLATOR = Collator.getInstance();

	/**
	 * Element's state.
	 */
	public @Getter @Setter(AccessLevel.PRIVATE) @NonNull T descriptor;
}
