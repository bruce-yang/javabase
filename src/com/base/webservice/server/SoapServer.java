package com.base.webservice.server;

import javax.xml.ws.Endpoint;

/**
 * 
 * @author why
 *
 */
public class SoapServer {
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/com.base.webservice.server.helloService",new HelloImpl());
	}
}
