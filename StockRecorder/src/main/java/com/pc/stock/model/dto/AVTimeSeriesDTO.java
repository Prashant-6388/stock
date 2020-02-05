package com.pc.stock.model.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.pc.stock.model.StockData;


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
