package com.pc.model.auth;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"username","email"})
})
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	@Size(max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 200)
	private String password;
	
	@NotBlank
	@Size(max = 20)
	private String email;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
				joinColumns = @JoinColumn(name="user_id"),
				inverseJoinColumns = @JoinColumn(name="role_id")
			)
	private Set<Role> roles = new HashSet<>();
	
	
	public User() {
	}
	
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
