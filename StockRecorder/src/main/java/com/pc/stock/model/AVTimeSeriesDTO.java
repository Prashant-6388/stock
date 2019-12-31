package com.pc.stock.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;


public class AVTimeSeriesDTO {

	private List<StockData> list;
	
	public List<StockData> getList() {
		return list;
	}

	public void setList(List<StockData> list) {
		this.list = list;
	}

	public AVTimeSeriesDTO() {
		list = new ArrayList<StockData>();
	}
	
}
