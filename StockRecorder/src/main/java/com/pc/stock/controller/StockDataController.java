package com.pc.stock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pc.stock.model.AVResponse;
import com.pc.stock.service.AVResponseExtractor;

@RestController
public class StockDataController {

	@Autowired
	RestTemplate restTemplate;

	private static final String REST_URL = "https://gturnquist-quoters.cfapps.io/api/random";
	private static final String REST_URL1 = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=MSFT&apikey=demo";
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/getStockPrice", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getStockPrice(@RequestParam(defaultValue = "0") int limit, @RequestParam String symbol) {
		logger.info("got name = {}", limit);
		AVResponse response1 = restTemplate.execute(REST_URL1, HttpMethod.GET, null, new AVResponseExtractor<String>());
		System.out.println("Response = " + response1);
		return "";
	}

}
