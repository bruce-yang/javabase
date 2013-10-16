package com.base.xml;

import java.io.InputStream;

public class ReadXmlFileStream {

	private static final String XML_FILE = "com/base/xml/addresses.xml";

	public static InputStream getXmlFileStream() {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(XML_FILE);
	}

}
