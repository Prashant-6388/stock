package com.pc.stock.enums;

public enum NewsCategory {
	BUSINESS("business"),
	ENTERTAINMENT("entertainment"),
	HEALTH("health"),
	SCIENCE("science"),
	SPORTS("sports"),
	TECHNOLOGY("technology");
		
	private String category;
	
	private NewsCategory(String category) {
		this.category = category;
	}

	public String toString() {
		return category;
	}
}
