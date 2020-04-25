package com.pc.stock.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.model.stock.StockData;

@Repository
public interface StockDataRepository extends JpaRepository<StockData, Integer>{

}
