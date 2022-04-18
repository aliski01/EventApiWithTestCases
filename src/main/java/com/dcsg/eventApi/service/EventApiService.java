package com.dcsg.eventApi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
@Service
public class EventApiService {
	
	@Value("${event_url}")
	private String eventUrl;

	@Value("${client_id}")
	private String clientId;

	@Value("${client_secret}")
	private String secretId;
	
		
	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSecretId() {
		return secretId;
	}

	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}
	
	public UriComponentsBuilder getUri(String url) {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		return builder;
	}
	public UriComponentsBuilder addClientIdAndSecret(UriComponentsBuilder builder)
	{
		return builder.queryParam("client_id", clientId)
		.queryParam("client_secret",secretId);
	}

	public String getUriBuilderForAllEvents()
	{
		 
		//UriComponentsBuilder builder = getUri(eventUrl);
		UriComponentsBuilder finalBuilder = addClientIdAndSecret(getUri(eventUrl));
		 
		return finalBuilder.toUriString();
	}
	
	public String getUriBuilderForEventById(String id)
	{
		String tempEventUrl = eventUrl + "/" +id; 
		
		//UriComponentsBuilder builder = getUri(tempEventUrl);
		UriComponentsBuilder finalBuilder = addClientIdAndSecret(getUri(tempEventUrl));
		 
		return finalBuilder.toUriString();
	}
	
	public String getUriBuilderSearch(String keyword)
	{
		
		//UriComponentsBuilder builder = getUri(eventUrl);
		UriComponentsBuilder finalBuilder = addClientIdAndSecret(getUri(eventUrl))
		 .queryParam("q", keyword);
		 
		return finalBuilder.toUriString();
	}
}
