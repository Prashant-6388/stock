package com.pc.stock.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.pc.model.stock.StockData;


public class AVTimeSeriesDTO {

	private Map<String, StockData> stockPrice;

	public Map<String, StockData> getStockPrice() {
		return stockPrice;
	}



	public void setStockPrice(Map<String, StockData> stockPrice) {
		this.stockPrice = stockPrice;
	}

	public AVTimeSeriesDTO() {
		stockPrice = new HashMap<String, StockData>();
	}
	
	public String toString() {
		return "Time Series contains "+stockPrice.keySet().size() +" days stock prices";
	}
}
