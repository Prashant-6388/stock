package com.pc.stock.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "requestLog")
public class RequestLog {

	@Id
	private String requestType;

	private LocalDateTime lastRequestSent;

	public RequestLog() {
		super();
	}
	
	public RequestLog(String requestType, LocalDateTime lastRequestSent) {
		this.requestType = requestType;
		this.lastRequestSent = lastRequestSent;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public LocalDateTime getLastRequestSent() {
		return lastRequestSent;
	}

	public void setLastRequestSent(LocalDateTime lastRequestSent) {
		this.lastRequestSent = lastRequestSent;
	}
}
