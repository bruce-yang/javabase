/**
 * Copyright (c) 2005-2010 woyo.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.base.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.utils.JsonUtil;
import com.base.utils.StringUtilsExtends;


public class HttpJsonClient {
	private static final Logger log = LoggerFactory.getLogger(HttpJsonClient.class);
	public HttpJsonClient() {
		super();
	}

	/**
	 * 使用连接池创建httpClient
	 * @return
	 */
	public static DefaultHttpClient getHttpClient(){
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
	    schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		HttpParams httpParams  = new BasicHttpParams();  
		cm.setMaxTotal(10);// 设置最大连接数  
		cm.setDefaultMaxPerRoute(5);// 设置每个路由默认最大连接数 
		HttpConnectionParams.setConnectionTimeout(httpParams, 60000);//设置连接超时
		HttpConnectionParams.setSoTimeout(httpParams, 60000);//设置读取超时
		 HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
	     HttpProtocolParams.setUseExpectContinue(httpParams, false);
	     httpParams.setParameter(ClientPNames.COOKIE_POLICY,CookiePolicy.IGNORE_COOKIES);
		DefaultHttpClient httpClient = new DefaultHttpClient(cm, httpParams);
		//httpClient.setCookieStore(null);
		httpClient.getCookieStore().clear();
		httpClient.getCookieStore().getCookies().clear();
	//	httpClient.setHttpRequestRetryHandler(new HttpJsonClient().new HttpRequestRetry());//外部类对象来创建内部类对象
		return httpClient;
	}
   
	//重连策略
	public class HttpRequestRetry implements HttpRequestRetryHandler{
		@Override
		public boolean retryRequest(IOException ex, int executionCount, HttpContext context) {
			// 设置恢复策略，在发生异常时候将自动重试3次  
            if (executionCount > 3) {    
                // 超过最大次数则不需要重试    
                return false;    
            }    
            if (ex instanceof NoHttpResponseException) {    
                return true; // 服务停掉则重新尝试连接    
            }    
            if (ex instanceof SSLHandshakeException) {    
                return false;  // SSL异常不需要重试  
            }
            HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);  
            boolean idempotent = (request instanceof HttpEntityEnclosingRequest);  
            if (!idempotent) {  
                return true;  // 请求内容相同则重试  
            }  
            return false;
		}
	}
	/**
	 * 动态创建http请求方式
	 * @param httpMethod
	 * @param uri
	 * @return
	 */
  @SuppressWarnings("unchecked")
  protected static <T> T createHttpUriRequest(HttpMethod httpMethod, URI uri) {
	        switch (httpMethod) {
	            case GET:
	                return (T) new HttpGet(uri);
	            case DELETE:
	                return (T) new HttpDelete(uri);
	            case HEAD:
	                return (T) new HttpHead(uri);
	            case OPTIONS:
	                return (T) new HttpOptions(uri);
	            case POST:
	                return (T) new HttpPost(uri);
	            case PUT:
	                return (T) new HttpPut(uri);
	            case TRACE:
	                return (T) new HttpTrace(uri);
	            default:
	                throw new IllegalArgumentException("Invalid HTTP method: " + httpMethod);
	        }
	    }
	 
	 enum HttpMethod {
	        GET,
	        POST,
	        HEAD,
	        OPTIONS,
	        PUT,
	        DELETE,
	        TRACE
	    }
	 
	/**
	 * �����������get����
	 * 
	 * @param url
	 *            URL��ַ
	 * @param params
	 *            get��Ĳ���key-value�ԣ�һ��һ������Ƕ��
	 * @return ���������ص�JSON�ַ���Ϣ
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException 
	 */
	public static String get(String url, Map<String, ?> params)
			throws ClientProtocolException, IOException, URISyntaxException {
		DefaultHttpClient httpclient =getHttpClient();
		try {
			if (params != null && !(params.isEmpty())) {
				List<NameValuePair> values = new ArrayList<NameValuePair>();
				for (Map.Entry<String, ?> entity : params.entrySet()) {
					BasicNameValuePair pare = new BasicNameValuePair(entity
							.getKey(), entity.getValue().toString());
					values.add(pare);

				}
				String str = URLEncodedUtils.format(values, "UTF-8");
				if (url.indexOf("?") > -1) {
					url += "&" + str;
				} else {
					url += "?" + str;
				}
			}
		    URL pageURL = new URL(url);
		    //防止pageUrl中出现空格竖杠等特殊字符导致httpget请求失败
	        URI uri = new URI(pageURL.getProtocol(), pageURL.getHost(), pageURL.getPath(), pageURL.getQuery(), null);
			HttpGet httpget =createHttpUriRequest(HttpMethod.GET, uri);
			httpget.setHeader("Pragma", "no-cache");
			httpget.setHeader("Cache-Control", "max-age=0, no-cache, no-store, must-revalidate");
			httpget.setHeader("Connection","keep-alive");
			httpget.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpget.setHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			 httpget.setHeader("Accept-Charset", "gbk,utf-8;q=0.7,*;q=0.7");
			httpget.setHeader("Referer", url);
			/*httpget.setHeader("Content-Encoding", "gzip");
			httpget.setHeader("Accept-Encoding", "gzip, deflate");*/
			//httpget.setHeader("Host", "s.taobao.com");
			 /*int temp = Integer.parseInt(Math.round(Math.random()*(MingSpiderService.UserAgent.length-1))+"");//随机生成浏览器
			httpget.setHeader("User-Agent", MingSpiderService.UserAgent[temp]);*/
			HttpResponse response=httpclient.execute(httpget);
			httpclient.getCookieStore().clear();
			int resStatu = response.getStatusLine().getStatusCode();
			String html = "";
			if (resStatu==HttpStatus.SC_OK) {//200正常 
                HttpEntity entity = response.getEntity();  
                if (entity!=null) {  
                    html = EntityUtils.toString(entity,"GBK");
                    EntityUtils.consume(entity);
                }  
            }  
			return html;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	
  public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException {
    String rdStr= RandomStringUtils.randomNumeric(9);
    log.info(rdStr);
    EqualsBuilder eb=new EqualsBuilder();
	String url="http://detailskip.taobao.com/json/ifq.htm?id=18278375481&sid="+rdStr+"&q=1";
	 String jsonStr=HttpJsonClient.get(url, null);
	 String str=StringUtilsExtends.substringBetween(jsonStr, ":{", "}");
	 Map<String,Integer> map=JsonUtil.getMap4Json("{"+str+"}");
	 log.info(map.get("quanity")+"");
  }
	/**
	 * �����������һ��http delete����
	 * 
	 * @param url
	 *            Ҫ�����URL
	 * @return ���������ص�JSON�ַ�
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String delete(String url) throws ClientProtocolException,
			IOException {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpDelete httpget = new HttpDelete(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(httpget, responseHandler);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * �������post�ַ�
	 * 
	 * @param url
	 *            Ҫ�����URL
	 * @param data
	 *            Ҫ���͵����
	 * @return ���������ص���Ϣ
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String post(String url, String data)
			throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);

			if (data != null) {
				ByteArrayEntity mult = new ByteArrayEntity(data
						.getBytes("UTF-8"));
				httpPost.setEntity(mult);
			}

			log.debug("begin to post url:" + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(httpPost, responseHandler);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
}
