package org.baoting.te.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.baoting.te.constaints.TeConstaints;

/**
 * 
 * ClassName: HttpClientHelper
 * @description
 * @author yin_changbao
 * @Date   Aug 3, 2015
 *
 */
public class HttpClientHelper {
	public static final Log logger = LogFactory.getLog(HttpClientHelper.class);

	public static String doPost(String url,String body) throws IOException {
		HttpPost post = new HttpPost(url);
		post.setHeader(TeConstaints.HTTP_HEAD_CONTENT_TYPE, TeConstaints.HTTP_HEAD_CONTENT_TYPE_VALUE);
		if(!StringUtil.isEmpty(body)){
			HttpEntity entity = new ByteArrayEntity(body.getBytes());
			post.setEntity(entity);
		}
		return sendRequest(post);
	}


	public static String doPut(String url,String body ) throws IOException {
		HttpPut request = new HttpPut(url);
		request.setHeader(TeConstaints.HTTP_HEAD_CONTENT_TYPE, TeConstaints.HTTP_HEAD_CONTENT_TYPE_VALUE);
		if (!StringUtil.isEmpty(body)) {
			List<NameValuePair> values = JsonHelper.json2Bean(body, ArrayList.class);
			request.setEntity(new UrlEncodedFormEntity(values));
		}
		return sendRequest(request);
	}


	public static String doGet(String url) {
		HttpGet request = new HttpGet(url);
		request.setHeader(TeConstaints.HTTP_HEAD_CONTENT_TYPE, TeConstaints.HTTP_HEAD_CONTENT_TYPE_VALUE);
		return sendRequest(request);
	}


	public static String doDelete(String url,String body){
		DeleteWithBodyMethod delete = new DeleteWithBodyMethod(url);
		delete.setHeader(TeConstaints.HTTP_HEAD_CONTENT_TYPE, TeConstaints.HTTP_HEAD_CONTENT_TYPE_VALUE);
		if(!StringUtil.isEmpty(body)){
			HttpEntity entity = new ByteArrayEntity(body.getBytes());
			delete.setEntity(entity);
		}
		return sendRequest(delete);
	}


	private static HttpClient getHttpClient() {

		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TeConstaints.CONNECT_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TeConstaints.SOCKET_TIMEOUT);

		return client;
	}
	
	
	private static String sendRequest(HttpUriRequest request) {
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

	
	static class DeleteWithBodyMethod extends HttpPost{
	    public DeleteWithBodyMethod(String url){
	        super(url);
	    }
	    @Override
	    public String getMethod() {
	        return "DELETE";
	    }
	}

}