package com.pc.stock.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.model.auth.Role;
import com.pc.stock.enums.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(ERole name);
}
