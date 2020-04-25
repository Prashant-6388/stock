package com.pc.model.stock;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class RequestLogId implements Serializable {
	
	private String requestType;

	private String requestKeyword;
	
	public RequestLogId() {
		super();
	}
	
	public RequestLogId(String requestType, String requestKeyword) {
		super();
		this.requestType = requestType;
		this.requestKeyword = requestKeyword;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	public String getRequestKeyword() {
		return requestKeyword;
	}

	public void setRequest_keyword(String requestKeyword) {
		this.requestKeyword = requestKeyword;
	}

	@Override
	public int hashCode() {
		return Objects.hash(requestType,requestKeyword);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestLogId other = (RequestLogId) obj;
		if (requestKeyword == null) {
			if (other.requestKeyword != null)
				return false;
		} else if (!requestKeyword.equals(other.requestKeyword))
			return false;
		if (requestType == null) {
			if (other.requestType != null)
				return false;
		} else if (!requestType.equals(other.requestType))
			return false;
		return true;
	}
}
	
