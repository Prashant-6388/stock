package com.pc.stock.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AVMetaDataDTO {
	
	private String information;
	
	private String symbol;
	
	private String lastrefreshed;
	
	private String interval;
	
	private String outputsize;
	
	private String timezone;
	
	public AVMetaDataDTO() {}

	public AVMetaDataDTO(String information, String symbol, String lastrefreshed, String interval, String outputSize,
			String timezone) {
		super();
		this.information = information;
		this.symbol = symbol;
		this.lastrefreshed = lastrefreshed;
		this.interval = interval;
		this.outputsize = outputSize;
		this.timezone = timezone;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
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
	
	public String getLastrefreshed() {
		return lastrefreshed;
	}

	public void setLastrefreshed(String lastrefreshed) {
		this.lastrefreshed = lastrefreshed;
	}

	public String getOutputsize() {
		return outputsize;
	}

	public void setOutputsize(String outputsize) {
		this.outputsize = outputsize;
	}
	
	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String toString() {
		return "MetaData    : \n"+
				"information   : "+information    +"\n" + 
				"symbol        : "+symbol         +"\n" + 
				"lastrefreshed : "+lastrefreshed  +"\n" + 
				"interval:     : "+interval       +"\n" +
				"outputsize:   : "+outputsize     +"\n" +
				"timezone      : "+timezone       +"\n";
	}
}
