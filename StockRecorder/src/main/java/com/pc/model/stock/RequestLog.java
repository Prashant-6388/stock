package com.pc.model.stock;

import java.time.LocalDate;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "request_Log")
public class RequestLog {

	@EmbeddedId
	private RequestLogId requestLogId;

	private LocalDate lastRequestSent;

	public RequestLog() {
		super();
	}

	public RequestLog(RequestLogId requestId, LocalDate lastRequestSent) {
		this.requestLogId = requestId;
		this.lastRequestSent = lastRequestSent;
	}

	public RequestLogId getRequestLogId() {
		return requestLogId;
	}

	public void setRequestLogId(RequestLogId requestId) {
		this.requestLogId = requestId;
	}

	public LocalDate getLastRequestSent() {
		return lastRequestSent;
	}

	public void setLastRequestSent(LocalDate lastRequestSent) {
		this.lastRequestSent = lastRequestSent;
	}

}
