package com.pc.model.stock;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stock")
public class Stock implements Serializable, Cloneable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="symbol", unique = true)
	private	String symbol;

	@Column(name = "description")
	private String description;
	
	@Column(name="stock_exchange")
	private String stockExchange;
	
	public Stock() {
	}
	
	public Stock(String symbol, String description, String stockExchange) {
		this.symbol = symbol;
		this.description = description;
		this.stockExchange = stockExchange;
	}

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
	
	public Stock clone() {
		try {
			return (Stock)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String toString() {
		return "\nSymbol : "+ symbol +
				"\nDescription : "+ description +
				"\nStock Exchange : "+ stockExchange;
				
	}
	
}
