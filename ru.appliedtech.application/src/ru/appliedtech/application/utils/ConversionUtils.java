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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.activation.DataSource;

import ru.appliedtech.application.AppException;
import ru.appliedtech.application.ApplicationStatus;

import lombok.val;


/**
 * Helper class used represent data in different scales of notation
 * @author Igor V. Pavlenko
 * @date 29.08.2009
 */
public abstract class ConversionUtils
{
	public abstract static class Converter<T, P>
	{
		/**
		 * Encodes data.
		 * @param toEncode Data to be encoded.
		 * @return Encoded data.
		 * @throws IllegalArgumentException
		 */
		public abstract P encode(T toEncode) throws IllegalArgumentException;
		
		/**
		 * Decodes data.
		 * @param toDecode Data to be decoded.
		 * @return Decoded data.
		 * @throws IllegalArgumentException
		 */
		public abstract T decode(P toDecode) throws IllegalArgumentException;
	}

	/**
	 * Hex conversions
	 */
	public static final Converter<byte[], String> HEX = new Converter<byte[], String>()
	{
		@Override
		public String encode(byte[] toEncode)
		{
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < toEncode.length; i++)
			{
				int halfbyte = (toEncode[i] >>> 4) & 0x0F;
				int two_halfs = 0;
				do
				{
					if ((0 <= halfbyte) && (halfbyte <= 9))
					{
						buffer.append((char) ('0' + halfbyte));
					}
					else
					{
						buffer.append((char) ('a' + (halfbyte - 10)));
					}
					halfbyte = toEncode[i] & 0x0F;
				} while (two_halfs++ < 1);
			}
			return buffer.toString();
		}

		@Override
		public byte[] decode(String toDecode)
		{
			int len = toDecode.length();
			byte[] data = new byte[len / 2];
			for (int i = 0; i < len; i += 2)
			{
				data[i / 2] = (byte) ((Character.digit(toDecode.charAt(i), 16) << 4) + Character.digit(toDecode.charAt(i + 1), 16));
			}
			return data;
		}
	};

	/**
	 * Hex conversions
	 */
	public static final Converter<byte[], String> BASE62 = new Converter<byte[], String>()
	{
		@Override
		public String encode(byte[] toEncode)
		{
			String base64 = BASE64.encode(toEncode);
			StringBuffer buffer = new StringBuffer();
			int length = base64.length();
			for (int i = 0; i < length; i++)
			{
				char ch = base64.charAt(i);
				switch (ch)
				{
					case 'i':
						buffer.append("ii"); //$NON-NLS-1$
						break;
					case '+':
						buffer.append("ip"); //$NON-NLS-1$
						break;
					case '/':
						buffer.append("is"); //$NON-NLS-1$
						break;
					case '=':
						buffer.append("ie"); //$NON-NLS-1$
						break;
					case '\n':
						// Strip out
						break;
					default:
						buffer.append(ch);
				}
			}
			return buffer.toString();
		}

		@Override
		public byte[] decode(String toDecode)
		{
			int length = toDecode.length();
			StringBuffer base64 = new StringBuffer(length);

			for (int i = 0; i < length; i++)
			{
				char ch = toDecode.charAt(i);

				if (ch == 'i')
				{
					i++;
					char code = toDecode.charAt(i);
					switch (code)
					{
						case 'i':
							base64.append('i');
							break;
						case 'p':
							base64.append('+');
							break;
						case 's':
							base64.append('/');
							break;
						case 'e':
							base64.append('=');
							break;
					}
				}
				else
				{
					base64.append(ch);
				}
			}

			return BASE64.decode(base64.toString());
		}
	};

	public static class Base64Converter extends Converter<byte[], String> {
		
		@Override
		public String encode(byte[] toEncode)
		{
			return new String(Base64Coder.encode(toEncode));
		}

		@Override
		public byte[] decode(String toDecode)
		{
			return Base64Coder.decode(toDecode);
		}
		
		public String encode(DataSource resourse) throws AppException {
			try {
				val input = resourse.getInputStream();
				val output = new ByteArrayOutputStream();
				FileUtils.copyStream(input, output, false);
				return String.format("data:%s;base64,%s", MimeTypes.getMimeType(resourse.getName()), ConversionUtils.BASE64.encode(output.toByteArray()));
			} catch (IOException e) {
				throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
			}
		}
	}
	
	/**
	 * Base64 conversions
	 */
	public static final Base64Converter BASE64 = new Base64Converter();
	
	public static final <T extends Serializable> Converter<T, byte[]> binary(final Class<T> clazz)
	{
		return new Converter<T, byte[]>()
		{
			@Override
			public byte[] encode(T toEncode) throws IllegalArgumentException
			{
				try
				{
					ObjectOutputStream oos = null;
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					try
					{
						oos = new ObjectOutputStream(bos);
						oos.writeObject(toEncode);
					}
					finally
					{
						if (oos != null)
						{
							oos.close();
						}
					}
					
					return bos.toByteArray();
				}
				catch (IOException e)
				{
					throw new IllegalArgumentException(e);
				}
			}

			@Override
			public T decode(byte[] toDecode) throws IllegalArgumentException
			{
				try
				{
					ObjectInputStream ois = null;
					ByteArrayInputStream bis = new ByteArrayInputStream(toDecode);
					try
					{
						ois = new ObjectInputStream(bis);
						return clazz.cast(ois.readObject());
					}
					finally
					{
						if (ois != null)
						{
							ois.close();
						}
					}
				}
				catch (IOException | ClassNotFoundException | ClassCastException e)
				{
					throw new IllegalArgumentException(e);
				}
			}
		};
	}
	
	public static final Converter<Serializable, byte[]> BINARY = binary(Serializable.class);
}
