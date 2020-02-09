package com.pc.stock.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pc.stock.model.News;
import com.pc.stock.model.RequestLog;
import com.pc.stock.model.Template;
import com.pc.stock.model.dto.NewsRequestDTO;
import com.pc.stock.model.dto.NewsResponse.Article;
import com.pc.stock.model.repo.NewsRepository;
import com.pc.stock.model.repo.RequestLogRepository;
import com.pc.stock.model.repo.TemplateRepository;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

@Service
public class NewsService {

	public static final Logger log = LoggerFactory.getLogger(NewsService.class);
	public static final String BASE_URL = "https://newsapi.org/v2/everything?";
	
	@Autowired
	TemplateRepository templateRepository;
	
	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	RequestLogRepository requestLogRepository;

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
	
	public List<NewsRequestDTO> convertToNewsRequestDTO(byte[] config) {
		try {
			XStream xstream = new XStream();
			return (List<NewsRequestDTO>)xstream.fromXML(new String(config));
		}catch(XStreamException ex) {
			log.error("Unable to convert template config for NewsDto",ex);
		}
		return null;
	}
	
	public void storeArticles(List<Article> articles) {
		List<News> newsList = new  ArrayList<>();
		for(Article article : articles) {
			News news = new News();
			news.setTitle(article.getTitle());
			news.setAuthor(article.getAuthor());
			news.setPublistedAt(LocalDateTime.parse(article.getPublishedAt()));
			news.setDescription(article.getDescription());
			news.setSource(article.getSource().getName());
			news.setUrl(article.getUrl());
			news.setUrlToImage(article.getUrlToImage());
			newsList.add(news);
		}
		try {
			newsRepository.saveAll(newsList);
		}catch(HibernateException he) {
			log.debug("Unable to persist news in DB.", he);
		}
	}
	
	public void updateLastRequestSent() {
		requestLogRepository.save(new RequestLog("NewsRequest",LocalDateTime.now()));
	}
}
