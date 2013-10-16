package com.base.webservice.cfx.client;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * 使用CXF实现方式时，生成客户端代码既可使用JAVA6的客户端生成工具，也可使用CXF特有的客户端生成工具：
 * 在命令行将当前目录切换到CXF 的bin 目录，然后运行wsdl2java –h 查看这个批处理命令的各个参数的作用，
 * 常用的方式就是wsdljava –p 包路径 –d 目标文件夹 wsdl 的url地址。现在我们将前面的WSDL生成客户端代码：
 * wsdl2java -p com.why.client –d E:\ http://127.0.0.1:8080/helloService?wsdl
 * 
 * @author why
 *
 */
public class SoapClient {
	public static void main(String[] args) throws ParseException, MalformedURLException {
		//1、使用标准的JAX-WS 的API 完成客户端调用
//		QName qName = new QName("http://service.why.com/","HelloService");
//		HelloService helloService = new HelloService(new URL("http://127.0.0.1:8080/helloService?wsdl"),qName);
//		Hello hello = (Hello) helloService.getPort(Hello.class);
		
		//2、使用了CXF 的JaxWsProxyFactoryBean 来访问Web 服务    
		JaxWsProxyFactoryBean soapFactoryBean = new JaxWsProxyFactoryBean();
		soapFactoryBean.setAddress("http://127.0.0.1:8080/helloService");
		soapFactoryBean.setServiceClass(Hello.class);
		Object o = soapFactoryBean.create();
		Hello hello = (Hello) o;
		
		
		
		hello.printContext();
		
		System.out.println("---------------------------------------------------");
		
		Customer customer = new Customer();
		customer.setName("why");
		DataSource ds = hello.selectCustomerByName(customer).getImageData().getDataSource();
		String attachmentMimeType = ds.getContentType();
		System.out.println(attachmentMimeType);
		try {
			InputStream is = ds.getInputStream();
			OutputStream os = new FileOutputStream("d:\\why_temp.jpg");
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("########################################");
		
		Customer c1 = new Customer();
		c1.setId(1);
		c1.setName("why");
		GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
//		c1.setBirthday(new XMLGregorianCalendarImpl(calendar));//cxf2.3中没有这个类了,用以下方式替换
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("1985-10-07"));
		try {
			c1.setBirthday(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		c1.setImageData(new DataHandler(new FileDataSource("d:\\c1.jpg")));
		
		Customer c2 = new Customer();
		c2.setId(2);
		c2.setName("abc");
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("1986-10-07"));
		//c2.setBirthday(new XMLGregorianCalendarImpl(calendar));
		try {
			c2.setBirthday(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		c2.setImageData(new DataHandler(new FileDataSource("d:\\c2.jpg")));
		
		Customer c = hello.selectMaxAgeCustomer(c1,c2);
		System.out.println(c.getName());
		
	}
}