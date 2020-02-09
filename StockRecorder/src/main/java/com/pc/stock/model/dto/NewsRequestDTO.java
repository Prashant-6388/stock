package com.pc.stock.model.dto;

import java.time.LocalDate;
import com.pc.stock.enums.NewsCategory;

import com.pc.stock.enums.SortBy;

public class NewsRequestDTO {

	String country;
	String language;
	NewsCategory category;
	String sources;
	String keyword;
	String keywordInTitle;
	String domain;  
	String excludeDomain;
	SortBy sortBy;
	LocalDate from;
	LocalDate to;
	int pageSize;
	int page;
	String apiKey;
	String source;
	
	public NewsRequestDTO(String keyword,String apiKey) {
		this.keyword = keyword;
		this.apiKey = apiKey;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public NewsCategory getCategory() {
		return category;
	}
	public void setCategory(NewsCategory category) {
		this.category = category;
	}
	public String getSources() {
		return sources;
	}
	public void setSources(String sources) {
		this.sources = sources;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getKeywordInTitle() {
		return keywordInTitle;
	}
	public void setKeywordInTitle(String keywordInTitle) {
		this.keywordInTitle = keywordInTitle;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getExcludeDomain() {
		return excludeDomain;
	}
	public void setExcludeDomain(String excludeDomain) {
		this.excludeDomain = excludeDomain;
	}
	public SortBy getSortBy() {
		return sortBy;
	}
	public void setSortBy(SortBy sortBy) {
		this.sortBy = sortBy;
	}
	public LocalDate getFrom() {
		return from;
	}
	public void setFrom(LocalDate from) {
		this.from = from;
	}
	public LocalDate getTo() {
		return to;
	}
	public void setTo(LocalDate to) {
		this.to = to;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
