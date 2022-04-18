package com.dcsg.eventApi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;


class EventApiServiceTest {
	Logger logger = LoggerFactory.getLogger(EventApiServiceTest.class);

	@Mock
	UriComponentsBuilder uBuilder;

	EventApiService service;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setup() {
		service = new EventApiService();

		service.setEventUrl("testUrl");
		service.setClientId("testClient");
		service.setSecretId("testclient_Secret");

		MockitoAnnotations.openMocks(this);

		logger.info(service.getEventUrl());

		String testUri = "https://api.seatgeek.com/2/events";
		when(uBuilder.toUriString()).thenReturn(testUri);

	}

	@Test
	void testEventUrl_NotNull() throws IOException {

		/*
		 * Properties props = new Properties();
		 * 
		 * System.out.println(System.getProperty("user.dir")); InputStream fileIn =
		 * Test.class.getClassLoader().getResourceAsStream(System.getProperty("user.dir"
		 * )+"\\src\\main\\resources\\application.properties"); props.load(fileIn); //
		 * assertNotNull(fileIn); System.out.println(props.getProperty("event_url"));
		 */
		// service.setEventUrl("testUrl");

		assertNotNull(service.getEventUrl());
	}

	@Test
	void testClientId_NotNull() {

		logger.info(service.getClientId());
		assertNotNull(service.getClientId());
	}

	@Test
	void testClientSecret_NotNull() {

		assertNotNull(service.getSecretId());
	}

	@Test
	void TestGetUriBuilderForAllEventsReturns_NotNull() {

		assertNotNull("A URI should be returned ", service.getUriBuilderForAllEvents());
	}

	@Test
	void testGetUriBuilderForEventById_NotNull() {

		// System.out.println(service.getUriBuilderForEventById("123"));

		assertNotNull("A URI should be returned ", service.getUriBuilderForEventById("123"));
	}

	@Test
	void testGetUriBuilderSearch_NotNull() {

		// System.out.println(service.getUriBuilderSearch("swift"));

		assertNotNull("A URI should be returned ", service.getUriBuilderSearch("swift"));
	}

}
