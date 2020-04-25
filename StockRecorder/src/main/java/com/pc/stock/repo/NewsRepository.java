package com.pc.stock.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.model.stock.News;
import com.pc.model.stock.News.NewsId;

@Repository
public interface NewsRepository extends JpaRepository<News, NewsId>{
	public List<News> findAllByOrderBySearchWordAsc();
}
