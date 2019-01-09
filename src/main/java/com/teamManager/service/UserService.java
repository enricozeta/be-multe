package com.teamManager.service;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;

import com.teamManager.dto.UserDTO;
import com.teamManager.model.Team;
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
	 * Update user.
	 *
	 * @param user
	 *            the user
	 * @return the user
	 */
	public User updateUser(UserDTO user);

	/**
	 * Creates the staff user.
	 *
	 * @param user
	 *            the user
	 * @return the user
	 * @throws Exception
	 *             the exception
	 */
	public User createStaffUser(UserDTO user) throws Exception;

	/**
	 * Gets the staff user.
	 *
	 * @return the staff user
	 * @throws Exception
	 *             the exception
	 */
	public User getStaffUser() throws Exception;

	/**
	 * Check staff user.
	 *
	 * @param team
	 *            the team
	 * @return true, if successful
	 */
	public boolean checkStaffUser(Team team);

	/**
	 * Change password.
	 *
	 * @param email
	 *            the email
	 * @param oldPassword
	 *            the old password
	 * @param newPassword
	 *            the new password
	 * @param forgot
	 *            the forgot
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public boolean changePassword(@NonNull String email, @NonNull String oldPassword, @NonNull String newPassword,
			Boolean forgot) throws Exception;

	/**
	 * Gets the authentication.
	 *
	 * @return the authentication
	 */
	public Authentication getAuthentication();

}
