package com.base.httpclient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIUtils;
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
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.base.utils.StringUtilsExtends;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * 
 * @author lucene_yang
 * @email yangfuchao2010@gmail.com
 * @date 2010-7-7下午03:05:59
 * @project_name htmlparser
 * 
 */
@SuppressWarnings("all")
public class HttpRemoteCall {
	private String encoding_whole = "UTF-8";
	private Logger log = Logger.getLogger(HttpRemoteUtil.class);
	private HttpClient httpClient;
	private int conPoolSize = 50;

	public HttpRemoteCall() {
	}

	public HttpRemoteCall(int conPoolSize) {
		this.conPoolSize = conPoolSize;
	}

	private HttpClient getHttpClient() {
		if (httpClient != null) {
			return httpClient;
		}
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
      
		// Create and initialize scheme registry
		SchemeRegistry schReg = new SchemeRegistry();  
        schReg.register(new Scheme("http", 80, PlainSocketFactory  
                .getSocketFactory()));  
        schReg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory())); 
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schReg);
		cm.setMaxTotal(10);
		HttpClient newHttpClient = new DefaultHttpClient(cm, params);
		// newHttpClient.getParams().setIntParameter("http.socket.timeout",3000);//设置超时，单位毫秒
		// set BROWSER_COMPATIBILITY,if not the login status can't get
		newHttpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,CookiePolicy.RFC_2965);
		HttpClientParams.setCookiePolicy(newHttpClient.getParams(),
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient = newHttpClient;
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 2500);
		httpClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 2000);// 设置连接时间
		return newHttpClient;
	}

	public String remoteCall(String url, JSONObject param, String strparam,
			String encoding) {
		try {
			URL login = new URL(url);
			List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
			if (null != strparam && !"".equals(strparam)) {
				httpParams.add(new BasicNameValuePair("strparam", "strparam"));
			}
			if (null != param && !"".equals(param)) {
				for (Iterator<Object> iterator = param.keys(); iterator
						.hasNext();) {
					String key = String.valueOf(iterator.next());
					httpParams.add(new BasicNameValuePair(key, param
							.getString(key)));
				}
			}
			URI uri = URIUtils.createURI(login.getProtocol(), login.getHost(),
					login.getPort(), login.getPath(), URLEncodedUtils.format(
							httpParams, encoding), null);
			// CookieStore cookieStore=new

			HttpPost httpPost = new HttpPost(uri);
			System.out.println("request uri——" + uri);
			
			//httpPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 2500);// 设置读取数据时间
			HttpClient httpclient = getHttpClient();
			HttpResponse response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
					|| (statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
					|| (statusCode == HttpStatus.SC_SEE_OTHER)
					|| (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
				String newUri = response.getLastHeader("Location").getValue();
				Logger.getLogger(HttpRemoteCall.class).info("newUri-" + newUri);
				httpclient = new DefaultHttpClient();
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
							int readSize = instream.read(buf, pos, buf.length
									- pos);
							if (readSize > 0) {
								pos += readSize;
							} else {
								break;
							}
						}
						if (pos != buf.length) {
							throw new RuntimeException(
									"remoteCall ,return content pos:" + pos
											+ " != buf.length:" + buf.length
											+ "");
						} else {
							return new String(buf, encoding);
						}
					}
				} finally {
					instream.close();
					EntityUtils.consume(response.getEntity());
				}
			} else {
				throw new RuntimeException(
						"remoteCall ,return content entity != null");
			}
		} catch (MalformedURLException e) {
			log.error(e);
			return null;
		} catch (URISyntaxException e) {
			log.error(e);
			return null;
		} catch (ClientProtocolException e) {
			log.error(e);
			return null;
		} catch (IOException e) {
			log.error(e);
			return null;
		}
	}

	public String remoteCallByPost(String url, JSONObject param, String encoding) {
		encoding = StringUtilsExtends.trimToNull(encoding);
		if (StringUtilsExtends.isNotBlank(encoding)) {
			encoding_whole = encoding;
		}
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			if (param != null) {
				ByteArrayEntity mult;
				mult = new ByteArrayEntity(param.toString().getBytes(
						encoding_whole));
				httpPost.setEntity(mult);
			}
			log.info("begin to post url:" + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			long start = System.currentTimeMillis();
			try {
				return httpclient.execute(httpPost, responseHandler);
			} finally {
				System.out.println("*************************" +  (System.currentTimeMillis() - start));
			}
		} catch (ClientProtocolException e) {
			log.error(e);
			return null;
		} catch (UnsupportedEncodingException e) {
			log.error(e);
			return null;
		} catch (IOException e) {
			log.error(e);
			return null;
		}

	}

	public String remoteCallByGet(String url, Map<String, ?> params) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			log.info("begin to get url:" + url);
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
			HttpGet httpget = new HttpGet(url);
			log.info("begin to get url:" + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				return httpclient.execute(httpget, responseHandler);
			} catch (ClientProtocolException e) {
				log.error(e);
				return null;
			} catch (IOException e) {
				log.error(e);
				return null;
			}
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	

	public String remoteCallDelete(String url, String encoding) {
		encoding = StringUtilsExtends.trimToNull(encoding);
		if (StringUtilsExtends.isNotBlank(encoding)) {
			encoding_whole = encoding;
		}
		HttpClient httpclient = getHttpClient();
		try {
			HttpDelete httpPost = new HttpDelete(url);
			//httpPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 2000);// 设置读取数据时间
			//httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 2000);
			//httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 1000);// 设置连接时间
			log.info("begin to post url:" + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(httpPost, responseHandler);
		} catch (ClientProtocolException e) {
			log.error(e);
			return null;
		} catch (UnsupportedEncodingException e) {
			log.error(e);
			return null;
		} catch (IOException e) {
			log.error(e);
			return null;
		}

	}

	public String remoteCallByCURL(String url, JSONObject param) {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		MultivaluedMap formData = new MultivaluedMapImpl();
		for (Iterator iterator = param.keys(); iterator.hasNext();) {
			Object key = (Object) iterator.next();
			String value = String.valueOf(param.get(key));
			formData.add(key, value);
		}
		ClientResponse response = webResource.type(
				"application/x-www-form-urlencoded").post(ClientResponse.class,
				formData);

		InputStream in = response.getEntityInputStream();
		InputStreamReader ir = new InputStreamReader(in);
		BufferedReader bu = new BufferedReader(ir);
		String json = null;
		try {
			json = bu.readLine();
		} catch (IOException e) {
			try {
				bu.close();
				ir.close();
				in.close();
			} catch (IOException e1) {
				log.error(e);
				return null;
			}
			log.error(e);
			return null;
		} finally {
			return json;
		}
	}
}
