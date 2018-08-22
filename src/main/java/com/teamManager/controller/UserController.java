package com.teamManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamManager.model.User;
import com.teamManager.service.UserService;

/**
 * The Class UserController.
 */
@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Update.
	 *
	 * @param user
	 *            the user
	 * @param oldPassword
	 *            the old password
	 * @param newPassword
	 *            the new password
	 * @return the boolean
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "admin/changePassword" }, method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody Boolean changePassword(@RequestBody User user, @NonNull String oldPassword,
			@NonNull String newPassword) throws Exception {
		return userService.changePassword(user, oldPassword, newPassword);
	}
}
