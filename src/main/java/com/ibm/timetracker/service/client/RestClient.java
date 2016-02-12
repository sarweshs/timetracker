package com.ibm.timetracker.service.client;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.ibm.timetracker.util.PropertiesFileUtil;

public class RestClient {
	static final int connectionTimeout;
	static final int socketTimeout;
	static final String serverUrl;
	static final RestTemplate restTemplate = new RestTemplate();
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
		SimpleClientHttpRequestFactory rf =
			    (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
		rf.setReadTimeout(connectionTimeout);
		rf.setConnectTimeout(socketTimeout);
		
	}

	public static String postDataOldWay(String jsonString) {
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
	
	public static String postData(String jsonString)
	{
		String plainCreds = "admin:admin";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64Creds);
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
			ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, request, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "fail";
		} 
	}

}
