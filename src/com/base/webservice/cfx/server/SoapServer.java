package com.base.webservice.cfx.server;

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
		//使用JAVA6默认实现发布Web Service，注意，要使用JAVA6默认实现方式需将构建路径中引入的CXF包删除。
//		Endpoint.publish("http://localhost:8080/helloService",new HelloImpl());
		
		/*
		 * 下面这种方式将javax.xml.ws.EndPoint 改为CXF 特有的API---JaxWsServerFactoryBean，
		 * 并且对服务端工厂Bean 的输入拦截器集合、输出拦截器集合中分别添加了日志拦截器（拦截器是CXF 的一项扩展功能，CXF 提供了很多拦截器实现，你也可以自己实现一种拦截器），
		 * 这样可以在Web 服务端发送和接收消息时输出信息。
		 */
		JaxWsServerFactoryBean soapFactoryBean = new JaxWsServerFactoryBean();
		soapFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
		soapFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
		// 注意这里是实现类不是接口
		soapFactoryBean.setServiceClass(HelloImpl.class);
		soapFactoryBean.setAddress("http://127.0.0.1:8080/helloService");
		soapFactoryBean.create();
	}
}
