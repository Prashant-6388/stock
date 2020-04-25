package com.pc.stock.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pc.model.stock.News;
import com.pc.model.stock.RequestLog;
import com.pc.model.stock.RequestLogId;
import com.pc.model.stock.Template;
import com.pc.stock.dto.NewsRequestDTO;
import com.pc.stock.dto.NewsResponse.Article;
import com.pc.stock.enums.RequestType;
import com.pc.stock.repo.NewsRepository;
import com.pc.stock.repo.RequestLogRepository;
import com.pc.stock.repo.TemplateRepository;
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
		LocalDate fromDate = null;
		RequestLogId requestId = new RequestLogId(RequestType.NEWSREQUEST.getRequestType(), newsRequestDto.getKeyword());
		Optional<RequestLog> result = requestLogRepository.findById(requestId);
		if(result != null && result.isPresent() )
			fromDate = result.get().getLastRequestSent();
		
		StringBuilder urlBuilder = new StringBuilder(BASE_URL);

		
		if (newsRequestDto.getApiKey() == null || newsRequestDto.getApiKey().isEmpty())
			return null;
		else
			urlBuilder.append("apiKey=" + newsRequestDto.getApiKey());

		if (newsRequestDto.getCategory() != null)
			urlBuilder.append("&category=" + newsRequestDto.getCategory().toString());

		if (newsRequestDto.getKeywordInTitle() != null && !newsRequestDto.getKeywordInTitle().isEmpty())
			urlBuilder.append("&qInTitle=" + newsRequestDto.getKeywordInTitle());

		if (newsRequestDto.getKeyword() != null && !newsRequestDto.getKeyword().isEmpty())
			urlBuilder.append("&q=" + newsRequestDto.getKeyword());

		if (newsRequestDto.getCountry() != null && !newsRequestDto.getCountry().isEmpty())
			urlBuilder.append("&country=" + newsRequestDto.getCountry());

		if (newsRequestDto.getPageSize() != 0)
			urlBuilder.append("&pageSize=" + newsRequestDto.getPageSize());

		if (newsRequestDto.getSortBy() != null)
			urlBuilder.append("&sortBy" + newsRequestDto.getSortBy());
		
		if (fromDate != null)
			urlBuilder.append("&from=" + fromDate);
		else if(newsRequestDto.getFrom()!= null)
			urlBuilder.append("&from=" + newsRequestDto.getFrom());
		
		return urlBuilder.toString();
	}
	
	public Template getNewsRequestTemplate(String configName) {
		return templateRepository.findByConfigName(configName);
	}
	
	public List<NewsRequestDTO> convertToNewsRequestDTO(String config) {
		try {
			XStream xstream = new XStream();
			xstream.setClassLoader(Thread.currentThread().getContextClassLoader());
			return (List<NewsRequestDTO>)xstream.fromXML(new String(config));
		}catch(XStreamException ex) {
			log.error("Unable to convert template config for NewsDto",ex);
		}
		return null;
	}
	
	public void storeArticles(List<Article> articles, String searchword) {
		List<News> newsList = new  ArrayList<>();
		for(Article article : articles) {
			News news = new News();
			news.setTitle(article.getTitle());
			news.setAuthor(article.getAuthor());
			news.setPublistedAt(LocalDateTime.parse(article.getPublishedAt(),
						DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.ENGLISH))
					);
			news.setDescription(article.getDescription());
			news.setSource(article.getSource().getName());
			news.setUrl(article.getUrl());
			news.setUrlToImage(article.getUrlToImage());
			news.setContent(article.getContent());
			news.setSearchWord(searchword);
			newsList.add(news);
		}
		try {
			newsRepository.saveAll(newsList);
		}catch(HibernateException he) {
			log.debug("Unable to persist news in DB.", he);
		}
	}
	
	
	public List<News> getAllNews() {
		return newsRepository.findAll();
	}
	
}
