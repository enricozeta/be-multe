package com.teamManager.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.teamManager.model.Role;
import com.teamManager.model.User;
import com.teamManager.repository.IRoleRepository;
import com.teamManager.repository.IUserRepository;

/**
 * The Class UserServiceImpl.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private IRoleRepository roleRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teamManager.service.UserService#findUserByEmail(java.lang.String)
	 */
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teamManager.service.UserService#saveUser(com.teamManager.model.User)
	 */
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teamManager.service.UserService#createStaffUser(com.teamManager.model
	 * .User)
	 */
	@Override
	public User createStaffUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role staffRole = roleRepository.findByRole("STAFF");
		user.setRoles(new HashSet<Role>(Arrays.asList(staffRole)));
		return userRepository.save(user);
	}

	@Override
	public boolean changePassword(@NonNull User user, @NonNull String oldPassword, @NonNull String newPassword)
			throws Exception {
		try {
			if (bCryptPasswordEncoder.matches(user.getPassword(), oldPassword)) {
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				userRepository.save(user);
				return true;
			} else {
				throw new Exception("Password update incomplete");
			}
		} catch (Exception e) {
			throw new Exception("Password update incomplete");
		}

	}

}
