package com.dcsg.eventApi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.dcsg.eventApi.errorHandler.APIException;
import com.dcsg.eventApi.service.EventApiService;
import com.dcsg.eventApi.validator.EventValidation;


@ExtendWith(MockitoExtension.class)
class EventApiControllerTest {
	Logger logger = LoggerFactory.getLogger(EventApiController.class);

	@Mock
	RestTemplate restTemplate;

	@Mock
	HttpEntity<String> httpEntity;
	@Mock
	EventApiService service;
	@Mock
	EventValidation validate;

	@InjectMocks
	EventApiController contr;

	@Test
	void testGetEventsList() 
	{

		String service1 = "uriForAllEvents";

		Mockito.when(service.getUriBuilderForAllEvents()).thenReturn(service1);

		ResponseEntity<String> responseEntity = new ResponseEntity<String>("sampleBodyString", HttpStatus.OK);
		logger.info(responseEntity.getBody());
		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.<HttpEntity<?>>any(), ArgumentMatchers.<Class<String>>any()))
		.thenReturn(responseEntity);

		ResponseEntity<String> eventList = contr.getEventsList();

		assertNotNull(eventList);

	}

	@Test
	void testGetEventsList_ForException() 
	{

		String service1 = "SampleException1";

		Mockito.when(service.getUriBuilderForAllEvents()).thenReturn(service1);

		ResponseEntity<String> responseEntity = new ResponseEntity<String>("sampleBodyString", HttpStatus.OK);
		logger.info(responseEntity.getBody());
		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.<HttpEntity<?>>any(), ArgumentMatchers.<Class<String>>any()))
		.thenThrow(RuntimeException.class);
		//assertThrows(RuntimeException.class, ()-> {contr.getEventsList();});

		assertThrows(RuntimeException.class,() -> contr.getEventsList());

	}

	@Test
	void testGetEventForId() 
	{
		String service2 = "UriForEventId";
		boolean valid = true;
		Mockito.when(validate.validateEventId(ArgumentMatchers.anyString())).thenReturn(valid);

		Mockito.when(service.getUriBuilderForEventById(ArgumentMatchers.anyString())).thenReturn(service2);

		ResponseEntity<String> responseEntity = new ResponseEntity<String>("sampleBodyString", HttpStatus.OK);
		logger.info(responseEntity.getBody());
		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.<HttpEntity<?>>any(), ArgumentMatchers.<Class<String>>any()))
		.thenReturn(responseEntity);

		ResponseEntity<String> eventforId = contr.getEventForId("123");

		assertNotNull(eventforId);
	}

	@Test
	void testGetEventForId_NotANumber() 
	{
		boolean valid = false;
		Mockito.when(validate.validateEventId(ArgumentMatchers.anyString())).thenReturn(valid);

		//ResponseEntity<String> eventforId = contr.getEventForId("12sa3");

		assertThrows(APIException.class, ()->contr.getEventForId("12sa3"));

	}
	@Test
	void testGetEventForId_ForInvalidId() 
	{
		boolean valid = true;
		Mockito.when(validate.validateEventId(ArgumentMatchers.anyString())).thenReturn(valid);
		String service1 = "sampleException2";

		Mockito.when(service.getUriBuilderForEventById(ArgumentMatchers.anyString())).thenReturn(service1);

		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.<HttpEntity<?>>any(), ArgumentMatchers.<Class<String>>any()))
		.thenThrow(HttpClientErrorException.NotFound.class);
		//ResponseEntity<String> eventforId = contr.getEventForId("123");

		assertThrows(APIException.class, ()->contr.getEventForId("123"));

	}

	@Test
	void testGetEventsListBySearch() {
		String service3 = "SampleUriForEventSearchedByKeyword";

		Mockito.when(service.getUriBuilderSearch(ArgumentMatchers.anyString())).thenReturn(service3);

		ResponseEntity<String> responseEntity = new ResponseEntity<String>("sampleBodyString", HttpStatus.OK);
		logger.info(responseEntity.getBody());
		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.<HttpEntity<?>>any(), ArgumentMatchers.<Class<String>>any()))
		.thenReturn(responseEntity);

		ResponseEntity<String> eventSearch = contr.getEventsListBySearch("swift");

		assertNotNull(eventSearch);
	}

	@Test
	void testGetEventsListBySearch_ForException() 
	{

		String service1 = "sampleException3";

		Mockito.when(service.getUriBuilderSearch(ArgumentMatchers.anyString())).thenReturn(service1);

		ResponseEntity<String> responseEntity = new ResponseEntity<String>("sampleBodyString3", HttpStatus.OK);
		logger.info(responseEntity.getBody());
		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.<HttpEntity<?>>any(), ArgumentMatchers.<Class<String>>any()))
		.thenThrow(RuntimeException.class);
		assertThrows(RuntimeException.class, ()-> {contr.getEventsListBySearch("NY");});

	}

}
