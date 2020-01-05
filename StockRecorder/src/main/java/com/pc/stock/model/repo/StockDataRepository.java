package com.pc.stock.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.stock.model.StockData;

@Repository
public interface StockDataRepository extends JpaRepository<StockData, Integer>{

}
