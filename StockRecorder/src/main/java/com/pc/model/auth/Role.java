package com.pc.model.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pc.stock.enums.ERole;

import lombok.Data;

@Entity
@Table
@Data
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private ERole name;
	
	public Role() {
		
	}
	public Role(ERole name) {
		this.name = name;
	}
}
