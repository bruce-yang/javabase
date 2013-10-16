package com.base.webservice.saaj.server;

import javax.xml.ws.Endpoint;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * 
 * @author why
 *
 */
public class SoapServer {
	public static void main(String[] args) {
		//ʹ��JAVA6Ĭ��ʵ�ַ���Web Service��ע�⣬Ҫʹ��JAVA6Ĭ��ʵ�ַ�ʽ�轫����·���������CXF��ɾ��
//		Endpoint.publish("http://localhost:8080/helloService",new HelloImpl());
		
		/*
		 * �������ַ�ʽ��javax.xml.ws.EndPoint ��ΪCXF ���е�API---JaxWsServerFactoryBean��
		 * ���ҶԷ���˹���Bean ���������������ϡ���������������зֱ��������־����������������CXF ��һ����չ���ܣ�CXF �ṩ�˺ܶ�������ʵ�֣���Ҳ�����Լ�ʵ��һ������������
		 * ���������Web ����˷��ͺͽ�����Ϣʱ�����Ϣ��
		 */
		JaxWsServerFactoryBean soapFactoryBean = new JaxWsServerFactoryBean();
		soapFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
		soapFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
		// ע��������ʵ���಻�ǽӿ�
		soapFactoryBean.setServiceClass(HelloImpl.class);
		soapFactoryBean.setAddress("http://127.0.0.1:8080/helloService");
		soapFactoryBean.create();
	}
}
