package com.ibm.timetracker.service.client;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.ibm.timetracker.util.PropertiesFileUtil;

public class RestClient {
	static final int connectionTimeout;
	static final int socketTimeout;
	static final String serverUrl;
	static
	{
		serverUrl = PropertiesFileUtil.getProperty("json.upload.server.url");
		String soTimeout = PropertiesFileUtil.getProperty("http.socket.timeout");
		if(soTimeout == null)
		{
			soTimeout = "3000";
		}
		socketTimeout = Integer.parseInt(soTimeout);
		
		String connTimeout = PropertiesFileUtil.getProperty("http.connection.timeout");
		if(connTimeout == null)
		{
			connTimeout = "3000";
		}
		connectionTimeout = Integer.parseInt(connTimeout);
	}

	public static String postData(String jsonString) {
		HttpParams my_httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(my_httpParams, connectionTimeout);
		HttpConnectionParams.setSoTimeout(my_httpParams, socketTimeout);
		DefaultHttpClient httpClient = new DefaultHttpClient(my_httpParams);
		try {

			HttpPost postRequest = new HttpPost(serverUrl);

			StringEntity input = new StringEntity(jsonString);
			input.setContentType("application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return "fail:could not upload";
			}

			httpClient.getConnectionManager().shutdown();

		} catch (MalformedURLException e) {
			return "fail";
		} catch (IOException e) {
			return "fail";
		}
		return "success";
	}

}
