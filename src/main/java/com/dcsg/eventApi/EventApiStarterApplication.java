package com.dcsg.eventApi;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EventApiStarterApplication {
	
	@Value("${time_out}")
	private int timeOut; 
	
	@Bean
	public RestTemplate getRestTemplate() {

		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		//Connect timeout
		clientHttpRequestFactory.setConnectTimeout(timeOut);

		//Read timeout
		//clientHttpRequestFactory.setReadTimeout(timeOut);		

		return new RestTemplate(clientHttpRequestFactory);
	}
	@Bean
	public ResponseEntity<String> getResponseEntityForTimeOut()
	{
		return new ResponseEntity<>("GATEWAY_TIMEOUT",HttpStatus.GATEWAY_TIMEOUT);
	}
	@Bean
	public ResponseEntity<String> getBadRequest()
	{
		return new ResponseEntity<>("Event ID should be a numaric value. HTTP Error status Code: 400",
				HttpStatus.BAD_REQUEST);
	}
	@Bean
	public ResponseEntity<String> getNotFound()
	{
		return new ResponseEntity<>("Event ID is not found. HTTP Error status Code: 404", HttpStatus.NOT_FOUND);
	}

	@Bean
	public HttpEntity<String> getEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		return new HttpEntity<String>(headers);
	}

	public static void main(String[] args) {
		SpringApplication.run(EventApiStarterApplication.class, args);

	}


}

