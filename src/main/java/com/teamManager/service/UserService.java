package com.teamManager.service;

import com.teamManager.model.User;

public interface UserService {

	public User findUserByEmail(String email);

	public void saveUser(User user);

	public User createStaffUser(User user);
}
