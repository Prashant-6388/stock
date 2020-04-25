package com.pc.stock.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

	@PostMapping("/registerToken")
	public String registerToken(String user, String token) {
		
		return "";
	}
}
