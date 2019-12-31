package com.pc.stock.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.pc.stock.model.AVResponse;

@RestController
public class StockDataController {
	
	@Autowired
	RestTemplate restTemplate;
	
	private static final String REST_URL = "https://gturnquist-quoters.cfapps.io/api/random";
	private static final String REST_URL1 = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=MSFT&apikey=demo";
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/getStockPrice/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getStockPrice(@PathVariable("name") String name) {
		logger.info("got name = {}",name);
		AVResponse response = restTemplate.getForObject(REST_URL1, AVResponse.class);//use this process to map//);
		
		Object response1 = restTemplate.execute(REST_URL1, HttpMethod.GET, null, new MyResponseExtractor<String>());//(REST_URL1, String.class);
//		pass this Gson and map to object
//		internal objectMapper -> override -> map to my Object
		String responseS = restTemplate.getForObject(REST_URL, String.class);
		System.out.println("Response = "+response);
		return "";
	}
	
}
