package com.pc.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "template")
public class Template {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "name")
	private String configName;
	
	@Column(name="config")
	private byte[] config;

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

	public byte[] getConfig() {
		return config;
	}

	public void setConfig(byte[] config) {
		this.config = config;
	}	
	
	public String toString() {
		return configName + "\n"+ new String(config);
	}
}
