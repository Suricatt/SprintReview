package ru.yarvet.sprint_description_builder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.MessageFormat;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

public class YouTrackClient {
	
	private final String API_URL;
	private final String key;
	private final String agileId;
	private final HttpClient client = HttpClientBuilder.create().build();
	
	public YouTrackClient(String aPI_URL, String key, String agileId) {
		this.API_URL = aPI_URL;
		this.key = key;
		this.agileId = agileId;
	}
	
	public String getAgiles() throws IOException, URISyntaxException{
		Object[] params = new Object[] { API_URL, agileId};
		String urlTemplate = "{0}/api/agiles/{1}?fields=id,name";
		String result = sendRequest(urlTemplate, params);
		return result;
	}
	
	public String getSprint() throws IOException, URISyntaxException {
		Object[] params = new Object[] { API_URL, agileId};
		String urlTemplate = "{0}/api/agiles/{1}/sprints?fields=id,name,finish";
		String result = sendRequest(urlTemplate, params);
		return result;
		
	}
	
	public String getIssueList(Sprint sprint) throws IOException, URISyntaxException {
		Object[] params = new Object[] { API_URL, URLEncoder.encode("#{"+sprint.getName() +"}", "UTF-8")};
		String urlTemplate = "{0}/api/issues?query={1}&fields=id,summary,customFields";
		String result = sendRequest(urlTemplate, params);
		return result;
	}
	
	public String getIssue(String id) throws HttpResponseException, ClientProtocolException, IOException, URISyntaxException {
		Object[] params = new Object[] { API_URL, id};
		String urlTemplate = "{0}/rest/issue/{1}?fields=id,summary,customFields";
		String result = sendRequest(urlTemplate, params);
		return result;
	}
	
	public String sendRequest(String urlTemplate, Object[] params)
			throws IOException, ClientProtocolException, HttpResponseException, URISyntaxException {
		
		HttpGet get = new HttpGet(MessageFormat.format(urlTemplate, params));
		get.addHeader("Authorization", "Bearer " + key);
		get.addHeader("Accept", "application/json");
		get.addHeader("Content-Type", "application/json");
		get.addHeader("Cache-Control", "no-cache");
		HttpResponse response = client.execute(get);
		String result = new BasicResponseHandler().handleResponse(response);
		return result;
	}
	
}
