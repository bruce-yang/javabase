package com.base.httpclient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import net.sf.json.JSONObject;



import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.base.utils.BeanUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPDigestAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;


public class HttpClientCall {
	private static String ip="";
	private static String port="";
	public static String remoteCallZJZserver(String url, JSONObject param,String user,String pass) {
//		ip=Configuation.getProxyConfigValue("proxy.ip");
//		port=Configuation.getProxyConfigValue("proxy.port");
//		DefaultApacheHttpClientConfig cc = new DefaultApacheHttpClientConfig();
//		cc.getProperties().put(
//				DefaultApacheHttpClientConfig.PROPERTY_PROXY_URI,ip+":"+port);
		
		Client client = Client.create();
		client.setConnectTimeout(5000);
		
		client.addFilter(new HTTPDigestAuthFilter(user,pass));
		MultivaluedMap formData = new MultivaluedMapImpl();
		for (Iterator iterator = param.keys(); iterator.hasNext();) {
			Object key = (Object) iterator.next();
			String value = String.valueOf(param.get(key));
			formData.add(key, value);
		}
		WebResource webResource = client.resource(url);
		String response = webResource.queryParams(formData).accept(
				MediaType.APPLICATION_JSON_TYPE).get(String.class);
		return response;
	}

	public static String remoteCallByPostNew(String url, JSONObject object) {
		String rs = "";
		HttpPost method = null;
		HttpClient httpClient = null;
		try {
			JSONObject jo = object;
			String body = jo.toString();
			httpClient = new DefaultHttpClient();
			method = new HttpPost(url);
			
			StringEntity entity=new StringEntity(body);
			entity.setContentType("application/json");
			entity.setContentEncoding( "utf-8");
			method.setEntity(entity);
			long start = System.currentTimeMillis();
			httpClient.execute(method);
			rs =EntityUtils.toString(method.getEntity());
			System.out.println("*************************"
					+ (System.currentTimeMillis() - start));
			//method.releaseConnection();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			//method.releaseConnection();
		}
		return rs;
	}

}
