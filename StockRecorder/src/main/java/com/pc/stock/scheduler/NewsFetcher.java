package com.pc.stock.scheduler;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.pc.stock.model.Template;
import com.pc.stock.model.dto.NewsRequestDTO;
import com.pc.stock.model.dto.NewsResponse;
import com.pc.stock.model.repo.TemplateRepository;
import com.pc.stock.service.NewsService;

public class NewsFetcher {

	private static final Logger log = LoggerFactory.getLogger(NewsFetcher.class);

	static String subscriptionKey = "YOUR-ACCESS-KEY";
	
	static String host = "https://api.cognitive.microsoft.com/bing";
    static String path = "/v7.0/localbusinesses/search";
    static String searchTerm = "Hotel in Bellevue";
    
    @Autowired
    NewsService newsService;
    
    @Autowired 
    TemplateRepository templateRepository;
    
    @Autowired
    RestTemplate restTemplate;
    
	public void fetchLatestNews(String searchQuery) {
		String requestURL;
		Template requestTemplate = newsService.getNewsRequestTemplate("news");
		List<NewsRequestDTO> newsDtos = newsService.getRequestDtos(requestTemplate.getConfig());
		for(NewsRequestDTO requestDto : newsDtos) {
			requestURL = newsService.createRequestURL(requestDto);
			requestAndProcessNewsResponse(requestURL);
		}
	}
	
	public void requestAndProcessNewsResponse(String url) {
		try {
			NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);
			//TODO : store in DB (testing pending)
			
		}
		catch(RestClientException ex) {
			log.error("News request failed for URL : "+url, ex);
		}
	}
	
	
	
}
