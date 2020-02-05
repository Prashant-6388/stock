package com.pc.stock.enums;

public enum Validation {

	REQUIRED("required"),
	NOTREQUIRED("not-required"),
	OPTIONAL("optional");
	
	String condition;
	
	private Validation(String condition) {
		this.condition = condition;
	}
	
	public String toSting() {
		return condition;
	}
}
