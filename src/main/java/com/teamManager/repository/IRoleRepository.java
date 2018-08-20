package com.teamManager.repository;

import org.springframework.data.repository.CrudRepository;

import com.teamManager.model.Role;

// TODO: Auto-generated Javadoc
/**
 * The Interface IRoleRepository.
 */
public interface IRoleRepository extends CrudRepository<Role, Integer> {

	/**
	 * Find by role.
	 *
	 * @param role the role
	 * @return the role
	 */
	Role findByRole(String role);

}
