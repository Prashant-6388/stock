package com.pc.stock;

import java.io.FileReader;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.resource.AbstractVersionStrategy;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.pc.stock.model.AVMetaDataDTO;
import com.pc.stock.model.AVResponse;
import com.pc.stock.model.AVTimeSeriesDTO;
import com.pc.stock.model.Stock;
import com.pc.stock.model.StockData;
import com.pc.stock.model.repo.StockDataRepository;
import com.pc.stock.model.repo.StockRepository;

@SpringBootTest
@AutoConfigureMockMvc
class StockRecorderApplicationTests {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private StockRepository stockRepo;
	
	@Autowired
	private StockDataRepository stockDataRepo;

	@Test
	void contextLoads() {
	}

	@Test
	void TestAlphaVantageController() {
		try {
			// mvc.perform(MockMvcRequestBuilders.get("/getStockPrice/HDFC"));
			Gson gson = new Gson();
			Map<String, LinkedTreeMap<String, Map<String, String>>> map = gson
					.fromJson(new FileReader("C:\\Users\\Prashant\\Desktop\\response.json"), Map.class);
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
}
