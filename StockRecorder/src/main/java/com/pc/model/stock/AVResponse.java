package com.pc.model.stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pc.stock.dto.AVMetaDataDTO;
import com.pc.stock.dto.AVTimeSeriesDTO;

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

	public String toString() {
		return metaData.toString() +" \n" + avTimeSeries.toString();
	}
	
	
}
