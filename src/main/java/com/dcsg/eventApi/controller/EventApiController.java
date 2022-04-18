package com.dcsg.eventApi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.dcsg.eventApi.service.EventApiService;
import com.dcsg.eventApi.validator.EventValidation;


@RestController
public class EventApiController {

	Logger logger = LoggerFactory.getLogger(EventApiController.class);

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EventValidation validate;
	@Autowired
	EventApiService service;
	@Autowired @Qualifier("getEntity")
	HttpEntity<String> entity;
	@Autowired @Qualifier("getResponseEntityForTimeOut")
	ResponseEntity<String> getTimeOut;
	@Autowired @Qualifier("getBadRequest")
	ResponseEntity<String> badRequest;
	@Autowired @Qualifier("getNotFound")
	ResponseEntity<String> notFound;

	// Returning all events
	@RequestMapping(value = "/template/events")
	public ResponseEntity<String> getEventsList() {
		String eventUri = service.getUriBuilderForAllEvents();
		logger.info(eventUri);

		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(eventUri, HttpMethod.GET, entity, String.class);
			// logger.info("response.getStatusCode() :: "+response.getStatusCode());
		}
		catch(ResourceAccessException re)
		{
			re.printStackTrace();
			return getTimeOut;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.debug("Exception occurred111 " + e.getMessage());
			throw new RuntimeException();

		}

		return response;
	}

	// Response for specific Event ID
	@RequestMapping(value = "/template/events/{id}")
	public ResponseEntity<String> getEventForId(@PathVariable("id") String id) {

		ResponseEntity<String> response = null;

		String eventUri = null;
		if (validate.validateEventId(id)) {
			eventUri = service.getUriBuilderForEventById(id);
		} else {
			return badRequest;
		}

		try {
			long t1 = System.currentTimeMillis();
			response = restTemplate.exchange(eventUri, HttpMethod.GET, entity, String.class);
			long t2 = System.currentTimeMillis();
			logger.info("response time: " + (t2 - t1));
			//logger.debug("response.getStatusCode() :: "+response.getStatusCode());


		}
		catch(ResourceAccessException re)
		{
			re.printStackTrace();
			return getTimeOut;
		}
		catch(HttpClientErrorException he)
		{
			logger.info("HttpClientErrorException caught!!");
			he.printStackTrace();
			return notFound;
		}

		return response;
	}

	// Search result for a string/keyword
	@RequestMapping(value = "/template/events/q={keyword}")
	public ResponseEntity<String> getEventsListBySearch(@PathVariable("keyword") String keyword) {

		ResponseEntity<String> response = null;
		String eventUri = service.getUriBuilderSearch(keyword);

		logger.info(eventUri);
		try {
			response = restTemplate.exchange(eventUri, HttpMethod.GET, entity, String.class);
			logger.info("response.getStatusCode() :: " + response.getStatusCode());
		}
		catch(ResourceAccessException re)
		{
			re.printStackTrace();
			return getTimeOut;
		}
		catch (Exception e) {
			logger.error("exception:: "+e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
		return response;

	}

}
