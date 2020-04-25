package com.pc.stock.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseExtractor;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.pc.model.stock.AVResponse;
import com.pc.model.stock.StockData;
import com.pc.stock.dto.AVMetaDataDTO;
import com.pc.stock.dto.AVTimeSeriesDTO;

/**
 * @author Prashant
 *
 * @param <T>
 */
@Service
public class AVResponseExtractor<T> implements ResponseExtractor<AVResponse> {
	
	private int noOfDays;
	
	public AVResponseExtractor() {
		super();
	}
	
	/**
	 * @param noOfDays : number of days for which you want to fetch time series data
	 * 					 specifying this as 0 will fetch complete data
	 * 
	 */
	public AVResponseExtractor(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	
	@Override
	public AVResponse extractData(ClientHttpResponse response) throws IOException {
		System.out.println("Trying to extract response "+response);
		String output = convert(response.getBody(), StandardCharsets.UTF_8);
		Gson gson = new Gson();
		Map<String, LinkedTreeMap<String, Map<String, String>>> map = gson.fromJson(output, Map.class);
		System.out.println("response = "+output);
		return translateToAVResponse(map, noOfDays);
	}

	public String convert(InputStream inputStream, Charset charset) throws IOException {
		 
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}
	
	private AVResponse translateToAVResponse(Map<String, LinkedTreeMap<String, Map<String, String>>> map, final int limit) {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context;
		AVResponse avResponse = new AVResponse();
		int seriesLimit = limit;
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

				for (Map.Entry<String, Map<String, String>> childEntry : value.entrySet()) {
					
					if(noOfDays !=0 && seriesLimit-- <= 0 ) 
						break;
					 
					System.out.println(childEntry.getKey());
					StockData stockData = new StockData();
					for (Map.Entry<String, String> subchildEntry : childEntry.getValue().entrySet()) {
						System.out.println("subchild = " + subchildEntry.getKey() + " : " + subchildEntry.getValue());
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
}
