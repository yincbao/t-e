package com.hoperun.oauth.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @ClassName: HttpClientHelper.java
 * @Description:
 * @author yin_changbao
 * @Date: May 16, 2016 11:50:12 AM
 *
 */
public class HttpClientHelper {
	public static final Log logger = LogFactory.getLog(HttpClientHelper.class);
	

	public static final int TIMEOUT = 30 * 60 * 1000;
	public static final int CONNECT_TIMEOUT = 15 * 60 * 1000;
	public static final int SOCKET_TIMEOUT = 30 * 60 * 1000;
	public static final String HTTP_HEAD_CONTENT_TYPE = "Content-Type";
	public static final String HTTP_HEAD_CONTENT_TYPE_VALUE = "application/json; charset=utf-8";
	
	public static Object executeConn(HttpRequestBase requestBase) {
		return HttpClientHelper.sendRequest(requestBase);
	}
	
	public static boolean isInvalidHttpRequest(HttpRequestBase requestBase){
		return requestBase==null||requestBase.getURI()==null||StringUtil.isEmpty(requestBase.getURI().toString());
	}
	
	public static void setHeaders(HttpRequestBase requestBase,Map<String,String> headerMap){
	
		if(headerMap!=null&&!headerMap.isEmpty()&&isInvalidHttpRequest(requestBase))
			for(Map.Entry<String, String> entry:headerMap.entrySet())
				requestBase.setHeader(entry.getKey(), entry.getValue());
		
	}

	public static String doPost(String url, String body) throws IOException {
		HttpPost post = new HttpPost(url);
		post.setHeader(HTTP_HEAD_CONTENT_TYPE, HTTP_HEAD_CONTENT_TYPE_VALUE);
		if (!StringUtil.isEmpty(body)) {
			HttpEntity entity = new ByteArrayEntity(body.getBytes());
			post.setEntity(entity);
		}
		return sendRequest(post);
	}

	public static String doGet(String url) {
		HttpGet request = new HttpGet(url);
		request.setHeader(HTTP_HEAD_CONTENT_TYPE, HTTP_HEAD_CONTENT_TYPE_VALUE);
		return sendRequest(request);
	}

	public static String doDelete(String url, String body) {
		DeleteWithBodyMethod delete = new DeleteWithBodyMethod(url);
		delete.setHeader(HTTP_HEAD_CONTENT_TYPE, HTTP_HEAD_CONTENT_TYPE_VALUE);
		if (!StringUtil.isEmpty(body)) {
			HttpEntity entity = new ByteArrayEntity(body.getBytes());
			delete.setEntity(entity);
		}
		return sendRequest(delete);
	}

	private static HttpClient getHttpClient() {

		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT);

		return client;
	}

	public static String sendRequest(HttpUriRequest request) {
		HttpClient client = getHttpClient();
		String response = null;
		try {
			HttpResponse res = client.execute(request);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				response = EntityUtils.toString(res.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			logger.error("HTTP server error", e);
			throw new RuntimeException(e);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return response;
	}

	static class DeleteWithBodyMethod extends HttpPost {
		public DeleteWithBodyMethod(String url) {
			super(url);
		}

		@Override
		public String getMethod() {
			return "DELETE";
		}
	}

}