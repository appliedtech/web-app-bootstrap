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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * Utility class to work with XML. Helps to produce an XML document to specified
 * file or stream. Also helps to get configured DocumentBuilder and Transformer
 * objects.
 * @author Igor V. Pavlenko
 * @date 29.08.2009
 */
public class XMLUtils
{
	private static DocumentBuilderFactory s_builderFactory;
	private static TransformerFactory s_transformerFactory;

	/**
	 * Creates and configures DocumentBuilder object
	 * @return DocumentBuilder object or null on failure
	 * @throws ParserConfigurationException If cannot create
	 */
	public static DocumentBuilder createBuilder()
			throws ParserConfigurationException
	{
		if (s_builderFactory == null)
		{
			synchronized (DocumentBuilderFactory.class)
			{
				if (s_builderFactory == null)
				{
					s_builderFactory = DocumentBuilderFactory.newInstance();
				}
			}
		}
		return s_builderFactory.newDocumentBuilder();
	}

	/**
	 * Creates and configures Transformer object
	 * @return Transformer object or null on failure
	 * @throws TransformerConfigurationException If cannot create
	 */
	public static Transformer createTransformer()
			throws TransformerConfigurationException
	{
		if (s_transformerFactory == null)
		{
			synchronized (TransformerFactory.class)
			{
				if (s_transformerFactory == null)
				{
					s_transformerFactory = TransformerFactory.newInstance();
				}
			}
		}
		Transformer transformer = s_transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
		return transformer;
	}

	/**
	 * Stores document using specified writer object. This method does not close
	 * writer object.
	 * @param document XML document
	 * @param transformer Transformer object to store document
	 * @param writer Writer object where this method stores XML document
	 * @throws TransformerException If cannot store
	 */
	public static void storeDocument(Document document,
			Transformer transformer, Writer writer) throws TransformerException
	{
		StreamResult result = new StreamResult(writer);
		transformer.transform(new DOMSource(document), result);
	}

	/**
	 * Stores document into specified file
	 * @param document XML document
	 * @param transformer Transformer object to store document
	 * @param file File where this method stores XML document
	 * @param encoding Encoding in with document should be stored
	 * @throws IOException If cannot store because of IO error
	 * @throws TransformerException If cannot store because transformer error
	 */
	public static void storeDocument(Document document,
			Transformer transformer, File file, String encoding)
			throws IOException, TransformerException
	{
		FileOutputStream stream = null;
		OutputStreamWriter writer = null;
		try
		{
			if (!file.exists())
			{
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			stream = new FileOutputStream(file);
			writer = new OutputStreamWriter(stream, encoding);
			storeDocument(document, transformer, writer);
		}
		finally
		{
			if (stream != null)
			{
				stream.close();
			}
		}
	}
}
