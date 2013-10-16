package com.base.webservice.saaj.server;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.soap.MTOM;

/**
 * ʹ���༶��ע��@WebService �ͱ�ע������ӿڵķ���������ΪWeb ����ʹ�������ע��Ľӿڵ����з�����������ΪWeb ����Ĳ�����
 * �����������ĳ������������ʹ�÷���ע��@Method ��exclude=true��
 * ����Ҳͨ���ѹ���ΪWeb����Ľӿڽ���SEI��Service EndPoint Interface������˵�ӿڡ�
 * 
 * ͨ��@MTOMע������MTOM���䷽ʽ�����ע����ڽӿڻ���ʵ�����϶�����
 * 
 * @author why
 *
 */
@WebService(name="Hello")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@MTOM
public interface Hello {
	public void printContext();
	public Customer selectCustomerByName(@WebParam(name = "c",header=true)Customer customer);
	public Customer selectMaxAgeCustomer(Customer c1, Customer c2);
}
