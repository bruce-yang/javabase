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
 * ʹ��CXFʵ�ַ�ʽʱ�����ɿͻ��˴���ȿ�ʹ��JAVA6�Ŀͻ������ɹ��ߣ�Ҳ��ʹ��CXF���еĿͻ������ɹ��ߣ�
 * �������н���ǰĿ¼�л���CXF ��bin Ŀ¼��Ȼ������wsdl2java �Ch �鿴�������������ĸ������������ã�
 * ���õķ�ʽ����wsdljava �Cp ��·�� �Cd Ŀ���ļ��� wsdl ��url��ַ���������ǽ�ǰ���WSDL���ɿͻ��˴��룺
 * wsdl2java -p com.why.client �Cd E:\ http://127.0.0.1:8080/helloService?wsdl
 * 
 * @author why
 *
 */
public class SoapClient {
	public static void main(String[] args) throws ParseException, MalformedURLException {
		//1��ʹ�ñ�׼��JAX-WS ��API ��ɿͻ��˵���
//		QName qName = new QName("http://service.why.com/","HelloService");
//		HelloService helloService = new HelloService(new URL("http://127.0.0.1:8080/helloService?wsdl"),qName);
//		Hello hello = (Hello) helloService.getPort(Hello.class);
		
		//2��ʹ����CXF ��JaxWsProxyFactoryBean ������Web ����    
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
//		c1.setBirthday(new XMLGregorianCalendarImpl(calendar));//cxf2.3��û���������,�����·�ʽ�滻
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