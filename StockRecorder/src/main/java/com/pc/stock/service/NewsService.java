package com.pc.stock.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pc.stock.model.Template;
import com.pc.stock.model.dto.NewsRequestDTO;
import com.pc.stock.model.repo.TemplateRepository;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

@Service
public class NewsService {

	public static final Logger log = LoggerFactory.getLogger(NewsService.class);
	public static final String BASE_URL = "https://newsapi.org/v2/everything?";
	
	@Autowired
	TemplateRepository templateRepository;

	public String createRequestURL(NewsRequestDTO newsRequestDto) {
		
		StringBuilder urlBuilder = new StringBuilder(BASE_URL);
		
		if (newsRequestDto.getApiKey() == null || newsRequestDto.getApiKey().isEmpty())
			return null;
		else
			urlBuilder.append("apiKey=" + newsRequestDto.getApiKey());

		if (newsRequestDto.getCategory() != null)
			urlBuilder.append("category=" + newsRequestDto.getCategory().toString());

		if (newsRequestDto.getKeywordInTitle() != null && !newsRequestDto.getKeywordInTitle().isEmpty())
			urlBuilder.append("qInTitle=" + newsRequestDto.getKeywordInTitle());

		if (newsRequestDto.getKeyword() != null && !newsRequestDto.getKeyword().isEmpty())
			urlBuilder.append("q=" + newsRequestDto.getKeyword());

		if (newsRequestDto.getFrom() != null)
			urlBuilder.append("from=" + newsRequestDto.getFrom());

		if (newsRequestDto.getCountry() != null && !newsRequestDto.getCountry().isEmpty())
			urlBuilder.append("country=" + newsRequestDto.getCountry());

		if (newsRequestDto.getPageSize() != 0)
			urlBuilder.append("pageSize=" + newsRequestDto.getPageSize());

		if (newsRequestDto.getSortBy() != null)
			urlBuilder.append("sortBy" + newsRequestDto.getSortBy());

		return urlBuilder.toString();
	}
	
	public Template getNewsRequestTemplate(String configName) {
		return templateRepository.findByConfigName(configName);
	}
	
	public List<NewsRequestDTO> getRequestDtos(byte[] config) {
		try {
			XStream xstream = new XStream();
			return (List<NewsRequestDTO>)xstream.fromXML(new String(config));
		}catch(XStreamException ex) {
			log.error("Unable to convert template config for NewsDto",ex);
		}
		return null;
	}
}
