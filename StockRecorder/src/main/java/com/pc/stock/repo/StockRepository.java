package com.pc.stock.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.model.stock.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
	public Stock findBySymbol(String symbol);
}
