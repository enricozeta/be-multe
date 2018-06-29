package com.teamManager.repository;

import org.springframework.data.repository.CrudRepository;

import com.teamManager.model.Role;

public interface IRoleRepository extends CrudRepository<Role, Integer> {

	Role findByRole(String role);

}
