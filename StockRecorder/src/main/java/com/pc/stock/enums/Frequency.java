package com.pc.stock.enums;

public enum Frequency {

	DAILY("Daily"),
	MONTHLY("Monthly"),
	WEEKLY("Weekly");	
	
	private String frequency;
	
	private Frequency(String frequency){
		this.frequency = frequency;
	}
	
	public String getFrequency() {
		return this.frequency;
	}
}
