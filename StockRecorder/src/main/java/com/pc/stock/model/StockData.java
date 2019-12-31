package com.pc.stock.model;

public class StockData {

	private String openingPrice;
	
	private String todayHigh;
	
	private String closingPrice;
	
	private String todayLow;

	private String volume;

	public String getOpeningPrice() {
		return openingPrice;
	}

	public void setOpeningPrice(String openingPrice) {
		this.openingPrice = openingPrice;
	}

	public String getTodayHigh() {
		return todayHigh;
	}

	public void setTodayHigh(String todayHigh) {
		this.todayHigh = todayHigh;
	}

	public String getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(String closingPrice) {
		this.closingPrice = closingPrice;
	}

	public String getTodayLow() {
		return todayLow;
	}

	public void setTodayLow(String todayLow) {
		this.todayLow = todayLow;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
}
