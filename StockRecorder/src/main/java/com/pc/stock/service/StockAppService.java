package com.pc.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pc.stock.model.Template;
import com.pc.stock.model.repo.TemplateRepository;

@Service
public class StockAppService {

	@Autowired
	TemplateRepository templateRepository;
	
	public List<Template> getAllTemplates(){
		return templateRepository.findAll();
	}
}
