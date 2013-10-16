/**
 * 
 */
package com.base.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.log4j.Logger;

/**
 * @author Administrator
 * 个人活动接口条用方法
 */
public class HttpPostEntityClient {
	private HttpClient httpClient;
	private int conPoolSize = 50;

	public HttpPostEntityClient() {
	}

	public HttpPostEntityClient(int conPoolSize) {
		this.conPoolSize = conPoolSize;
	}

	private HttpClient getHttpClient() {
		if (httpClient != null) {
			return httpClient;
		}
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, conPoolSize);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		// Create and initialize scheme registry
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 80));

		// Create an HttpClient with the ThreadSafeClientConnManager.
		// This connection manager must be used if more than one thread will
		// be using the HttpClient.
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
		HttpClient newHttpClient = new DefaultHttpClient(cm, params);
	   //newHttpClient.getParams().setIntParameter("http.socket.timeout",3000);//设置超时，单位毫秒
		  // 设置连接超时时间(单位毫秒)
		ConnManagerParams.setTimeout(params, 3000);
		
		// set BROWSER_COMPATIBILITY,if not the login status can't get
	  newHttpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2965);
	  HttpClientParams.setCookiePolicy(newHttpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		/*
		 * HttpGet httpget = new HttpGet("http://youxi.woyo.com:8080/"); //
		 * Override the default policy for this request
		 * httpget.getParams().setParameter( ClientPNames.COOKIE_POLICY,
		 * CookiePolicy.BROWSER_COMPATIBILITY);
		 */

		// newHttpClient.getConnectionManager().releaseConnection(arg0, arg1,
		// arg2);
		httpClient = newHttpClient;
		return newHttpClient;
	}

	public String remoteCall(String url, JSONObject param, String strparam, String encoding) throws URISyntaxException,
			IllegalStateException, IOException {
        
		URL login = new URL(url);
		List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
		if (null != strparam && !"".equals(strparam)) {
			httpParams.add(new BasicNameValuePair("strparam", "strparam"));
		}
		if (null != param && !"".equals(param)) {
			for (Iterator<Object> iterator = param.keys(); iterator.hasNext();) {
				String key = String.valueOf(iterator.next());
				httpParams.add(new BasicNameValuePair(key, param.getString(key)));

			}
		}
		URI uri = URIUtils.createURI(login.getProtocol(), login.getHost(), login.getPort(), login.getPath(),
				URLEncodedUtils.format(httpParams, encoding), null);
		// CookieStore cookieStore=new
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(new UrlEncodedFormEntity(httpParams, encoding));
	    System.out.println("11111111111111111111111111"+uri);
		// HttpMethod method = new GetMethod(uri.toString());
		// method.getParams().setCookiePolicy(org.apache.commons.httpclient.cookie.CookiePolicy.IGNORE_COOKIES);
		// method.setRequestHeader("Cookie", cookie);
		// httpPost.setHeader("Cookie", cookie);
		HttpClient httpclient = getHttpClient();
		//ResponseHandler<String> handler=new BasicResponseHandler();
		HttpResponse response = httpclient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY) || (statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
				|| (statusCode == HttpStatus.SC_SEE_OTHER) || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
			String newUri = response.getLastHeader("Location").getValue();
			Logger.getLogger(HttpRemoteCall.class).info("newUri-" + newUri);
			httpclient = new DefaultHttpClient();
			/*
			 * cookie header set
			 */
			
			httpPost = new HttpPost(newUri);
			response = httpclient.execute(httpPost);
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				if (entity.isChunked()) {
					byte[] buf = new byte[1024 * 8];
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					while (true) {
						int readSize = instream.read(buf);
						if (readSize > 0) {
							baos.write(buf, 0, readSize);
						} else {
							break;
						}
					}
					return new String(baos.toByteArray(), encoding);
				} else {
					byte[] buf = new byte[(int) entity.getContentLength()];
					int pos = 0;
					while (true) {
						int readSize = instream.read(buf, pos, buf.length - pos);
						if (readSize > 0) {
							pos += readSize;
						} else {
							break;
						}
					}
					if (pos != buf.length) {
						throw new RuntimeException("remoteCall ,return content pos:" + pos + " != buf.length:"
								+ buf.length + "");
					} else {
						return new String(buf, encoding);
					}
				}
			} finally {
				instream.close();
				//httpclient.getConnectionManager().shutdown();
			}
		} else {
			throw new RuntimeException("remoteCall ,return content entity != null");
		}
	}
}
