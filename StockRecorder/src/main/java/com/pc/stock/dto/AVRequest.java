package com.pc.stock.dto;

public class AVRequest {

	private String function;
	private String symbol;
	private String interval;
	private String outputSize;
	private String datatype;
	private String keyword;
	private String apiKey;
	
	public AVRequest() {
		super();
	}

	public AVRequest(String function, String symbol, String interval, String outputSize, String datatype, String keyword, String apiKey) {
		super();
		this.function = function;
		this.symbol = symbol;
		this.interval = interval;
		this.outputSize = outputSize;
		this.datatype = datatype;
		this.keyword = keyword;
		this.apiKey = apiKey;
	}
	
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getOutputSize() {
		return outputSize;
	}
	public void setOutputSize(String outputSize) {
		this.outputSize = outputSize;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String toString() {
		return "\nfunction= "+ function +"\n" +
				"symbol= "+ symbol +"\n" +
				"interval= "+ interval +"\n" +
				"outputSize= "+ outputSize +"\n" +
				"datatype= "+ datatype +"\n" +
				"keyword= "+keyword +"\n";		
	}
}
