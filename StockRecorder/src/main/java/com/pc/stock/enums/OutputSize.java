package com.pc.stock.enums;

public enum OutputSize {

	COMPACT("compact"),
	FULL("full");
	
	private String outputSize;
	
	private OutputSize(String outputSize) {
		this.outputSize=outputSize;
	}
	
	public String getOutputSize() {
		return this.outputSize;
	}
}
