package com.pc.stock.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.model.stock.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {
	
	Template findByConfigName(String configName);
}
