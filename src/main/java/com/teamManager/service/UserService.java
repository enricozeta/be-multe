package com.teamManager.service;

import com.teamManager.model.User;

/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Find user by email.
	 *
	 * @param email
	 *            the email
	 * @return the user
	 */
	public User findUserByEmail(String email);

	/**
	 * Save user.
	 *
	 * @param user
	 *            the user
	 */
	public void saveUser(User user);

	/**
	 * Creates the staff user.
	 *
	 * @param user
	 *            the user
	 * @return the user
	 */
	public User createStaffUser(User user);
}
