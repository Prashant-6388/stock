package com.pc.stock.enums;

public enum RequestType {
	NEWSREQUEST("NewsRequest"),
	AVREQUEST("AVRequest");	
	
	String requestType;

	private RequestType(String requestType) {
		this.requestType = requestType;
	}
	
	public String getRequestType() {
		return this.requestType;
	}


}
