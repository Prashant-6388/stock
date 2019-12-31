package com.pc.stock.rest.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class AlphaVantageClient {

	@Autowired
	public RestTemplate restTemplate;
	
	private static final String REST_URL = "https://gturnquist-quoters.cfapps.io/api/random";
	
	public static void main(String args[]) {
		AlphaVantageClient client = new AlphaVantageClient();
		String response = client.restTemplate.getForObject(REST_URL,String.class);
		System.out.println("Response = "+response);
	}
}
