package com.teamManager.service;

import org.springframework.lang.NonNull;

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

	/**
	 * Change password.
	 *
	 * @param user
	 *            the user
	 * @param oldPassword
	 *            the old password
	 * @param newPassword
	 *            the new password
	 * @return true, if successful
	 * @throws Exception
	 */
	public boolean changePassword(@NonNull User user, @NonNull String oldPassword, @NonNull String newPassword)
			throws Exception;

}
