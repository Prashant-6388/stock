package com.pc.stock.enums;

public enum Interval {

	ONE_MINUTE("1min"),
	FIVE_MINUTE("5min"),
	FIFTEEN_MINUTE("15min"),
	HALF_HOURL("30min"),
	HOUR("60min");
	
	private String interval;
	
	private Interval(String interval) {
		this.interval = interval;
	}
	
	public String getInterval() {
		return this.interval;
	}
}
