package com.pc.stock.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.stock.model.News;
import com.pc.stock.model.News.NewsId;

@Repository
public interface NewsRepository extends JpaRepository<News, NewsId>{

}
