package com.pc.stock.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pc.model.stock.AVResponse;
import com.pc.model.stock.News;
import com.pc.stock.constant.AVConstants;
import com.pc.stock.dto.AVRequest;
import com.pc.stock.service.AVResponseExtractor;
import com.pc.stock.service.AVService;
import com.pc.stock.service.NewsService;

@Controller
public class StockDataController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired 
	AVService avService;
	
	@Autowired
	NewsService newsService;
	
	private static final String REST_URL = "https://gturnquist-quoters.cfapps.io/api/random";
	private static final String REST_URL1 = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=MSFT&apikey=demo";
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/getStockPrice", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getStockPrice(@RequestParam(defaultValue = "0") int limit, @RequestParam String symbol,
			@RequestParam(required = false) String function, @RequestParam String apikey, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "json") String dataType, @RequestParam(required = false) String outputSize,
			@RequestParam(required = false) String interval) {
		logger.info("got name = {}", limit);
		AVRequest request = new AVRequest(function, symbol, interval, outputSize, dataType, keyword, apikey);
		StringBuilder paramURL = new StringBuilder(AVConstants.AVREQUSET_BASE_URL + "apikey="+apikey);
		boolean isValidRequest = avService.validateAndBuildRequestURL(request,paramURL);
		if(isValidRequest == false) 
			return "invalid request, please check your request";
		
		
		return "";
	}
	
	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value="/showNews")
	public String showNews(Model model) {
		List<News> newsList = newsService.getAllNews();
		model.addAttribute("newsList", newsList);
		return "showNews";
	}
}
