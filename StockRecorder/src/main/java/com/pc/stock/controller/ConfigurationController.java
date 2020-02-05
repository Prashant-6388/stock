package com.pc.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfigurationController {

	@RequestMapping("/configFile")
	public String loadConfigs(Model model) {
		return "config";
	}
}

