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

import java.nio.ByteBuffer;
import java.util.UUID;

import lombok.val;

public class Base62UUID implements java.io.Serializable, Comparable<Base62UUID> {
	private static final long serialVersionUID = 8015148180557622098L;
	
	private final UUID uuid;
	
	public Base62UUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	public UUID uuid() {
		return uuid;
	}
	
	public String getUuid() {
		return toString();
	}
	
	@Override
	public String toString() {
		return ConversionUtils.BASE62.encode(toBytes());
	}
	
	public byte[] toBytes() {
		val bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}
	
	public static Base62UUID fromBytes(byte[] bytes) {
		val bb = ByteBuffer.wrap(bytes);
		val msb = bb.getLong(0);
		val lsb = bb.getLong(8);
		return new Base62UUID(new UUID(msb, lsb));
	}
	
	public static Base62UUID fromString(String string) {
		return fromBytes(ConversionUtils.BASE62.decode(string));
	}

	@Override
	public int compareTo(Base62UUID o) {
		return uuid.compareTo(o.uuid);
	}

	@Override
	public int hashCode() {
		return uuid.hashCode() ^ Base62UUID.class.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Base62UUID other = (Base62UUID) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
