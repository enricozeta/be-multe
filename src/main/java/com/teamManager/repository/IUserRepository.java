package com.teamManager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import com.teamManager.model.User;

/**
 * The Interface IUserRepository.
 */
public interface IUserRepository extends CrudRepository<User, Long> {

	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the user
	 */
	public User findByEmail(@NonNull String email);

}
