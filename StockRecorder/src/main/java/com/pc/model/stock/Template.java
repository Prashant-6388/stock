package com.pc.model.stock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "template")
public class Template {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "name")
	private String configName;
	
	@Lob
	@Column(name = "config")
	private String config;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}	
	
	public String toString() {
		return configName + "\n"+ new String(config);
	}
}
