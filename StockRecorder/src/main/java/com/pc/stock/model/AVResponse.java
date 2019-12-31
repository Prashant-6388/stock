package com.pc.stock.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AVResponse {
	AVMetaDataDTO metaData;

	AVTimeSeriesDTO avTimeSeries;
	
	public AVTimeSeriesDTO getAvTimeSeries() {
		return avTimeSeries;
	}

	public void setAvTimeSeries(AVTimeSeriesDTO avTimeSeries) {
		this.avTimeSeries = avTimeSeries;
	}

	public AVMetaDataDTO getMetaData() {
		return metaData;
	}

	public void setMetaData(AVMetaDataDTO metaData) {
		this.metaData = metaData;
	}

	
	
}
