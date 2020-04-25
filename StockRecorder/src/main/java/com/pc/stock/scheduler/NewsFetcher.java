package com.pc.stock.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.pc.model.stock.Template;
import com.pc.stock.dto.NewsRequestDTO;
import com.pc.stock.dto.NewsResponse;
import com.pc.stock.dto.NewsResponse.Article;
import com.pc.stock.enums.RequestType;
import com.pc.stock.repo.TemplateRepository;
import com.pc.stock.service.NewsService;
import com.pc.stock.service.StockAppService;
import com.thoughtworks.xstream.XStream;

@Component
public class NewsFetcher {

	private static final Logger log = LoggerFactory.getLogger(NewsFetcher.class);
	public static final String OKAY = "ok";

	static String NEWS_APIKEY = "226075c95fd74daba9736c25c911666c";

	static String host = "https://api.cognitive.microsoft.com/bing";
	static String path = "/v7.0/localbusinesses/search";
	static String searchTerm = "Hotel in Bellevue";

	@Autowired
	NewsService newsService;

	@Autowired
	StockAppService stockAppService;

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	RestTemplate restTemplate;

	@Scheduled(cron = "0 0 10-19 * * MON-FRI")
	public void fetchLatestNews() {
		log.debug("fetching news");
		Template requestTemplate = newsService.getNewsRequestTemplate("newsConfig");
		if (requestTemplate != null) {
			List<NewsRequestDTO> newsDtos = newsService.convertToNewsRequestDTO(requestTemplate.getConfig());
			log.debug("get newsDTO");
			String requestURL;
			for (NewsRequestDTO requestDto : newsDtos) {
				requestURL = newsService.createRequestURL(requestDto);
				requestAndProcessNewsResponse(requestURL, requestDto.getKeyword());
			}
		}
	}

	@PostConstruct
	public void init() {
		log.debug("inserting News template");

		NewsRequestDTO inputHDFC = new NewsRequestDTO("HDFC", NEWS_APIKEY);
		inputHDFC.setSource("CNBC,the-times-of-india,Moneycontrol.com,TechCrunch,google-news"); // inputHDFC.setCountry("in");
		NewsRequestDTO inputAXIS = new NewsRequestDTO("AXIS", NEWS_APIKEY);
		inputAXIS.setSource("CNBC,the-times-of-india,Moneycontrol.com,TechCrunch,google-news"); // inputHDFC.setCountry("in");
		NewsRequestDTO inputReliance = new NewsRequestDTO("REL", NEWS_APIKEY);
		inputReliance.setSource("CNBC,the-times-of-india,Moneycontrol.com,TechCrunch,google-news");// inputHDFC.setCountry("in");
		NewsRequestDTO inputTCS = new NewsRequestDTO("TCS", NEWS_APIKEY);
		inputHDFC.setSource("CNBC,the-times-of-india,Moneycontrol.com,TechCrunch,google-news");
		NewsRequestDTO inputInfo = new NewsRequestDTO("INFOSYS", NEWS_APIKEY);
		inputHDFC.setSource("CNBC,the-times-of-india,Moneycontrol.com,TechCrunch,google-news");
		
		List<NewsRequestDTO> inputs = new ArrayList<>();
		inputs.add(inputHDFC);
		inputs.add(inputAXIS);
		inputs.add(inputReliance);
		inputs.add(inputTCS);
		inputs.add(inputInfo);
		
		log.debug("inputs = " + inputs);

		XStream xStream = new XStream();
		String inputXML = xStream.toXML(inputs);
		log.debug("inputXML :\n" + inputXML);

		Template template = templateRepository.findByConfigName("newsConfig");
		if (template == null) {
			template = new Template();
			template.setConfigName("newsConfig");
		}

		template.setConfig(inputXML);

		templateRepository.save(template);

		log.debug("Completed news template initialization");
	}

	public void requestAndProcessNewsResponse(String url, String keyword) {
		try {
			NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);
			// TODO : store in DB (testing pending)
			if (response.getStatus().equalsIgnoreCase(OKAY)) {
				response.setSearchWord(keyword);

				// need to get List of valid sources
				List<String> sources = new ArrayList();
				sources.add("Moneycontrol.com");
				sources.add("CNBC");
				sources.add("The Times of India");
				sources.add("Bloombergquint.com");
				sources.add("Business-standard.com");
				sources.add("Kitco.com");

				List<Article> filteredArticles = response.getArticles().stream()
						.filter(article -> sources.contains(article.getSource().getName()))
						.collect(Collectors.toList());

				response.setArticles(filteredArticles);

				newsService.storeArticles(response.getArticles(), keyword);
				stockAppService.updateLastRequestSent(RequestType.NEWSREQUEST.getRequestType(), keyword);
			}
		} catch (RestClientException ex) {
			log.error("News request failed for URL : " + url, ex);
		}
	}
}
