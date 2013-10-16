package com.base.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParseXml {

	private static Address address = new Address();
	
	
	public static void main(String[] args) {

		long lasting = System.currentTimeMillis();
		
		try {
			InputStream in = ReadXmlFileStream.getXmlFileStream();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(in);
			Element root = document.getDocumentElement();
			NodeList valueNode = root.getElementsByTagName("value");
			System.out.println("addresses:" + root + root.getChildNodes() + valueNode.getLength());
			
			for( int i=0; i<valueNode.getLength(); i++) {
				System.out.println(i);
				address.setNo(root.getElementsByTagName("no").item(i).getFirstChild().getNodeValue());
				address.setAddr(root.getElementsByTagName("addr").item(i).getFirstChild().getNodeValue());
				System.out.println(address);
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("运行时间：" + (System.currentTimeMillis() - lasting)

	              + " 毫秒");


	}

}
