package com.pc.stock.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.stock.model.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {
	
	Template findByConfigName(String configName);
}
