package com.pc.stock.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="stock")
public class Stock implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="symbol", unique = true)
//	@OneToMany
//	@JoinColumn(name="symbol")
	private	String symbol;
	
//	public Set<StockData> getSymbol() {
//		return symbol;
//	}
//	public void setSymbol(Set<StockData> symbol) {
//		this.symbol = symbol;
//	}

	@Column(name = "description")
	private String description;
	
	@Column(name="stock_exchange")
	private String stockExchange;
//	
//	@OneToMany
//	@JoinColumn(name="symbol")
//	private List<StockData> stockData;
//	
//	public Set<StockData> getStockData() {
//		return stockData;
//	}
//	public void setStockData(Set<StockData> stockData) {
//		this.stockData = stockData;
//	}
//	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStockExchange() {
		return stockExchange;
	}
	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}
	
	public String toString() {
		return "\nSymbol : "+ symbol +
				"\nDescription : "+ description +
				"\nStock Exchange : "+ stockExchange;
				
	}
	
}
