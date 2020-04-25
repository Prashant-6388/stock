package com.pc.stock;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.pc.model.stock.AVResponse;
import com.pc.model.stock.News;
import com.pc.model.stock.RequestLog;
import com.pc.model.stock.RequestLogId;
import com.pc.model.stock.Stock;
import com.pc.model.stock.StockData;
import com.pc.model.stock.Template;
import com.pc.notification.service.PushNotificationService;
import com.pc.stock.constant.AVConstants;
import com.pc.stock.dto.AVMetaDataDTO;
import com.pc.stock.dto.AVRequest;
import com.pc.stock.dto.AVTimeSeriesDTO;
import com.pc.stock.dto.NewsRequestDTO;
import com.pc.stock.dto.NewsResponse;
import com.pc.stock.dto.NewsResponse.Article;
import com.pc.stock.enums.Frequency;
import com.pc.stock.enums.Function;
import com.pc.stock.enums.NewsCategory;
import com.pc.stock.enums.OutputSize;
import com.pc.stock.repo.NewsRepository;
import com.pc.stock.repo.RequestLogRepository;
import com.pc.stock.repo.StockDataRepository;
import com.pc.stock.repo.StockRepository;
import com.pc.stock.repo.TemplateRepository;
import com.pc.stock.scheduler.AVTimeSeriesFetcher;
import com.pc.stock.scheduler.NewsFetcher;
import com.pc.stock.service.AVResponseExtractor;
import com.pc.stock.service.AVService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

@SpringBootTest
@AutoConfigureMockMvc
class StockRecorderApplicationTests {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private StockRepository stockRepo;
	
	@Autowired
	private StockDataRepository stockDataRepo;
	
	@Autowired
	private TemplateRepository templateRepository;
	
	@Autowired
	private RequestLogRepository requestLogRepository;
	
	@Autowired
	AVService avService;
	
	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	PushNotificationService pushNotificationService;
	
	private static final String AV_APIKEY = "NH0JRAHL2FB49D8K";
	private static final String NEWS_APIKEY = "226075c95fd74daba9736c25c911666c";

	@Autowired
	NewsFetcher newsFetcher;
	
//	@Autowired
//	private AVTimeSeriesFetcher scheduler;
	
	@Test
	void contextLoads() {
	}

	@Test
	void TestAlphaVantageController() {
		try {
			// mvc.perform(MockMvcRequestBuilders.get("/getStockPrice/HDFC"));
			Gson gson = new Gson();
			Map<String, LinkedTreeMap<String, Map<String, String>>> map = gson
					.fromJson(new FileReader("C:\\Users\\Prashant\\Desktop\\response1.json"), Map.class);
			AVResponse avResponse = translateToAVResponse(map);
			System.out.println(avResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private AVResponse translateToAVResponse(Map<String, LinkedTreeMap<String, Map<String, String>>> map) {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context;
		AVResponse avResponse = new AVResponse();
		for (Map.Entry<String, LinkedTreeMap<String, Map<String, String>>> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "/" + entry.getValue());
			if (entry.getKey().contains("Meta Data")) {
				AVMetaDataDTO avmDTO = new AVMetaDataDTO();
				context = new StandardEvaluationContext(avmDTO);
				for (Map.Entry<String, Map<String, String>> childEntry : entry.getValue().entrySet()) {
					System.out.println(childEntry.getKey() + ":" + childEntry.getValue());
					Expression exp = parser.parseExpression(
							childEntry.getKey().split(" ", 2)[1].replace(" ", "").trim().toLowerCase());
					exp.setValue(context, childEntry.getValue());
				}
				avResponse.setMetaData(avmDTO);
			}
			if (entry.getKey().contains("Time Series")) {
				LinkedTreeMap<String, Map<String, String>> value = entry.getValue();
				System.out.println(value);
				AVTimeSeriesDTO avTimeSeries = new AVTimeSeriesDTO();
				for (Map.Entry<String, Map<String,String>> childEntry : value.entrySet()) {
					System.out.println(childEntry.getKey());
					StockData stockData = new StockData();
					for (Map.Entry<String, String> subchildEntry : childEntry.getValue().entrySet()) {
						System.out.println("subchild = "+subchildEntry.getKey() + " : "+subchildEntry.getValue());
						if(subchildEntry.getKey().contains("open"))
							stockData.setOpeningPrice(Double.parseDouble(subchildEntry.getValue()));
						if(subchildEntry.getKey().contains("high"))
							stockData.setTodayHigh(Double.parseDouble(subchildEntry.getValue()));
						if(subchildEntry.getKey().contains("low"))
							stockData.setTodayLow(Double.parseDouble(subchildEntry.getValue()));
						if(subchildEntry.getKey().contains("close"))
							stockData.setClosingPrice(Double.parseDouble(subchildEntry.getValue()));
						if(subchildEntry.getKey().contains("volume"))
							stockData.setVolume(Integer.parseInt(subchildEntry.getValue()));
					}
					avTimeSeries.getStockPrice().put(childEntry.getKey(), stockData);
				}
				avResponse.setAvTimeSeries(avTimeSeries);
			}

		}
		return avResponse;
	}

	@Test
	@Transactional
	@Rollback(false)
	void TestStockRepo() {
		/*
		 * Stock in = new Stock(); in.setSymbol("HDFC"); in.setDescription("Test");
		 * in.setStockExchange("NYS"); stockRepo.save(in);
		 */
		
		Stock stock = stockRepo.getOne(1);
		System.out.println("Stock = "+stock);

		Stock stock1 = stockRepo.getOne(3);
		System.out.println("Stock = "+stock1);
		
		
		StockData stockData = new StockData();
		stockData.setStock(stock);
		stockData.setClosingPrice(10.00);
		stockData.setDate(Timestamp.valueOf(LocalDateTime.now()));
		stockData.setTodayHigh(10.00);
		stockData.setVolume(100);
		stockDataRepo.save(stockData);
		System.out.println("stockData = "+stockData);
		
		StockData stockData1 = new StockData();
		stockData1.setStock(stock1);
		stockData1.setClosingPrice(20.00);
		stockData1.setDate(Timestamp.valueOf(LocalDateTime.now()));
		stockData1.setTodayHigh(20.00);
		stockData1.setVolume(200);
		stockDataRepo.save(stockData1);
		System.out.println("stockData = "+stockData1);
	}
	
	@Test
	public void testXsteamToXML() {
		AVRequest inputHDFC = new AVRequest(Function.TIME_SERIES_DAILY.toString(),"NSE:HDFC", null, null, "json", null, AV_APIKEY);
		AVRequest inputAxis = new AVRequest(Function.TIME_SERIES_DAILY_ADJUSTED.toString(),"NSE:AXIS", null, null, "json", null, AV_APIKEY);
		AVRequest inputRel = new AVRequest(Function.TIME_SERIES_DAILY.toString(),"MSFT", null, null, "json", null, AV_APIKEY);
		
		List<AVRequest> inputs = Lists.newArrayList(inputHDFC, inputAxis, inputRel);
		System.out.println("inputs = "+inputs);
		
		XStream xStream = new XStream();
		String inputXML = xStream.toXML(inputs);
		System.out.println("inputXML :\n"+inputXML);
	}
	
	@Test
	public void testXsteamFromXML() {
		AVRequest inputHDFC = new AVRequest(Function.TIME_SERIES_DAILY.toString(),"NSE:HDFC", null, null, "json", null, AV_APIKEY);
		AVRequest inputAxis = new AVRequest(Function.TIME_SERIES_DAILY_ADJUSTED.toString(),"NSE:AXIS", null, null, "json", null, AV_APIKEY);
		AVRequest inputRel = new AVRequest(Function.TIME_SERIES_DAILY.toString(),"MSFT", null, null, "json", null, AV_APIKEY);
		
		List<AVRequest> inputs = Lists.newArrayList(inputHDFC, inputAxis, inputRel);
		System.out.println("inputs = "+inputs);
		
		XStream xStream = new XStream();
		String inputXML = xStream.toXML(inputs);
		System.out.println("inputXML :\n"+inputXML);
		
		
		
		List<AVRequest> list = (List<AVRequest>) xStream.fromXML(inputXML);
		System.out.println("From XML : \n");
		list.forEach(item -> System.out.println(item));
		
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void blobRepoTest() {
		AVRequest inputHDFC = new AVRequest(Function.TIME_SERIES_DAILY.toString(),"NSE:HDFC", null, null, "json", null, AV_APIKEY);
		AVRequest inputAxis = new AVRequest(Function.TIME_SERIES_DAILY_ADJUSTED.toString(),"NSE:AXIS", null, null, "json", null, AV_APIKEY);
		AVRequest inputRel = new AVRequest(Function.TIME_SERIES_DAILY.toString(),"MSFT", null, null, "json", null, AV_APIKEY);
		
		List<AVRequest> inputs = Lists.newArrayList(inputHDFC, inputAxis, inputRel);
		System.out.println("inputs = "+inputs);
		
		XStream xStream = new XStream();
		String inputXML = xStream.toXML(inputs);
		System.out.println("inputXML :\n"+inputXML);
		
		Template template = templateRepository.findByConfigName("inputConfig");
		if(template == null) {
			template = new Template() ;
			template.setConfigName("inputConfig");
		}
		
		template.setConfig(inputXML);
		
		templateRepository.save(template);
		
	}
	
	@Test
	@Rollback(true)
	public void testScheduler() {
		Template template = templateRepository.findByConfigName("inputConfig");
		
		List<AVResponse> responses = new ArrayList<>();
		List<String> urls = avService.createAVRequestURLs();
		urls.forEach(url -> System.out.println(url));
	}
	
	@Test
	public void testEnum() {
		System.out.println(OutputSize.FULL);
		System.out.println(OutputSize.COMPACT);
		System.out.println(Frequency.DAILY);
	}
	
	@Test
	@Rollback(false)
	public void testValidationTemplate() {
		AVRequest avRequest = new AVRequest();
		avRequest.setFunction("required");
		avRequest.setSymbol("required");
		avRequest.setInterval("not-required");
		avRequest.setDatatype("optional");
		avRequest.setOutputSize("optional");
		avRequest.setKeyword("not-required");
		
		XStream xStream = new XStream();
		String inputXML = xStream.toXML(avRequest);
		System.out.println("inputXML :\n"+inputXML);
		
		Template template = templateRepository.findByConfigName("TIME_SERIES_DAILY");
		if(template == null) {
			template = new Template();
			template.setConfigName("TIME_SERIES_DAILY");
		}
		template.setConfig(inputXML);
		templateRepository.save(template);
	}
	
	@Test
	public void testXstreamConversion() {
		Template template = templateRepository.findByConfigName(Function.TIME_SERIES_DAILY.toString());
		XStream xstream = new XStream();
		xstream.addPermission(new AnyTypePermission());
		String validationXML = new String(template.getConfig());
		AVRequest validator = (AVRequest)xstream.fromXML(validationXML);
		System.out.println(""+validator);
	}
	
	@Test
	public void testRequestValidation() {
		AVRequest request = new AVRequest("TIME_SERIES_DAILY", "HDFC", null, null, "json", null, AV_APIKEY);
		StringBuilder url = new StringBuilder(AVConstants.AVREQUSET_BASE_URL);
		boolean isValidRequest = avService.validateAndBuildRequestURL(request,url);
	}
	
	@Test
	public void testCreateURL() {
		avService.createAVRequestURLs();
	}
	
	@Test
	public void testNewsAPIResponseConversion() {
		try {
			String responseFromAPI = new String(Files.readAllBytes(Paths.get("D:\\Programming\\NewsAPI-Json-Response.json")));
			Gson gson = new Gson();
			NewsResponse response = gson.fromJson(responseFromAPI, NewsResponse.class);
			System.out.println("Total Articles in response = "+response.getArticles().size());
			List<Article> articles = response.getArticles();
			System.out.println("Source = "+articles.get(0).getSource().getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	@Rollback(true)
	@Transactional
	public void testNewsRepo() {
		News news = new News();
		news.setAuthor("PC");
		news.setTitle("Test");
		news.setPublistedAt(LocalDateTime.now());
		news.setDescription("This is just a test");
		news.setUrl(null);
		
		newsRepository.save(news);
	}
	
	@Test
	public void blobRepoNewsTest() {
		NewsRequestDTO inputHDFC = new NewsRequestDTO("HDFC", NEWS_APIKEY); inputHDFC.setSource("CNBC,the-times-of-india,Moneycontrol.com,TechCrunch,google-news"); //inputHDFC.setCountry("in");
		NewsRequestDTO inputAXIS = new NewsRequestDTO("AXIS", NEWS_APIKEY); inputAXIS.setSource("CNBC,the-times-of-india,Moneycontrol.com,TechCrunch,google-news"); //inputHDFC.setCountry("in");
		NewsRequestDTO inputReliance = new NewsRequestDTO("REL", NEWS_APIKEY); inputReliance.setSource("CNBC,the-times-of-india,Moneycontrol.com,TechCrunch,google-news");// inputHDFC.setCountry("in");
			
		List<NewsRequestDTO> inputs = Lists.newArrayList(inputHDFC, inputAXIS, inputReliance);
		System.out.println("inputs = "+inputs);
		
		XStream xStream = new XStream();
		String inputXML = xStream.toXML(inputs);
		System.out.println("inputXML :\n"+inputXML);
		
		Template template = templateRepository.findByConfigName("newsConfig");
		if(template == null) {
			template = new Template() ;
			template.setConfigName("newsConfig");
		}
		
		template.setConfig(inputXML);
		
		templateRepository.save(template);
		
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testNewsFetcher() {
		newsFetcher.fetchLatestNews();
	}
	
	@Test
	public void testRequestLogRepo() {
		requestLogRepository.save(new RequestLog(new RequestLogId("NewsRequest", "Test1"), LocalDate.now()));
		requestLogRepository.save(new RequestLog(new RequestLogId("NewsRequest", "Test2"), LocalDate.now()));
		requestLogRepository.save(new RequestLog(new RequestLogId("NewsRequest", "Test3"), LocalDate.now()));
	}
	
	@Test
	public void testNotification() {
		pushNotificationService.sendSamplePushNotification();
	}
}
