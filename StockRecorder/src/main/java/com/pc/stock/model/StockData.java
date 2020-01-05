package com.pc.stock.model;


import java.sql.Timestamp;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="stockdata")
public class StockData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	@Column(name="open")
	private double openingPrice;
	
	@Column(name="high")
	private double todayHigh;
	
	@Column(name="close")
	private double closingPrice;
	
	@Column(name="low")
	private double todayLow;

	@Column(name="volumn")
	private int volume;

	@Column(name="date")
	private Timestamp date;
		
	@OneToOne
	@JoinColumn(name = "symbol")
	private Stock stock;

//	public String getSymbol() {
//		return symbol;
//	}
//
//	public void setSymbol(String symbol) {
//		this.symbol = symbol;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getOpeningPrice() {
		return openingPrice;
	}

	public void setOpeningPrice(double openingPrice) {
		this.openingPrice = openingPrice;
	}

	public double getTodayHigh() {
		return todayHigh;
	}

	public void setTodayHigh(double todayHigh) {
		this.todayHigh = todayHigh;
	}

	public double getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(double closingPrice) {
		this.closingPrice = closingPrice;
	}

	public double getTodayLow() {
		return todayLow;
	}

	public void setTodayLow(double todayLow) {
		this.todayLow = todayLow;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	
	  public Stock getStock() { return stock; }
	  
	  public void setStock(Stock stock) { this.stock = stock; }
	 
	
	public String toString() {
		return "StockData = \n" +
				"Stock = "+stock +" \n" +
				"High = "+ todayHigh;
	}
}
