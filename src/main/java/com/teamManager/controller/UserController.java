package com.teamManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamManager.dto.UserDTO;
import com.teamManager.model.User;
import com.teamManager.repository.IUserRepository;
import com.teamManager.service.UserService;

/**
 * The Class UserController.
 */
@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private IUserRepository userRepository;

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
	public @ResponseBody Boolean changePassword(@NonNull String email, @NonNull String oldPassword,
			@NonNull String newPassword, Boolean forgot) throws Exception {
		return userService.changePassword(email, oldPassword, newPassword, forgot);
	}

	@RequestMapping(value = { "user" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody UserDTO get() throws Exception {
		String email = userService.getAuthentication().getName();
		if (email != null) {
			User user = userRepository.findByEmail(email);
			if (user != null) {
				UserDTO userDTO = new UserDTO();
				String fullName = user.getName() != null ? user.getName() : "";
				fullName = fullName + (user.getLastName() != null ? (" " + user.getLastName()) : "");
				userDTO.setFullName(fullName);
				userDTO.setEmail(user.getEmail());
				userDTO.setRoles(user.getRoles());
				userDTO.setName(user.getName());
				userDTO.setSurname(user.getLastName());
				userDTO.setTeam(user.getTeam().getName());

				return userDTO;
			} else {
				throw new Exception("User logged not found");
			}
		} else {
			throw new Exception("User logged not found");
		}
	}
}
