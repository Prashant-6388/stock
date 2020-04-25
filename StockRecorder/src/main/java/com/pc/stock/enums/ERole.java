package com.pc.stock.enums;

public enum ERole {
	ADMIN("admin"),
	MODERATOR("moderator"),
	USER("user");
	
	String role;
	
	private ERole(String role) {
		this.role = role;
	}
}
