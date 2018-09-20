package com.teamManager.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.teamManager.model.Role;
import com.teamManager.model.Team;
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
	private TeamService teamService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Value("${test}")
	private String test;

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
	public User createStaffUser(User user) throws Exception {

		// set user's fields
		Team currentTeam = teamService.getCurrentTeam();
		String name = currentTeam.getName();
		user.setEmail(name);
		user.setName(name);
		user.setTeam(currentTeam);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);

		Role staffRole = roleRepository.findByRole("STAFF");
		if (staffRole != null) {
			user.setRoles(new HashSet<Role>(Arrays.asList(staffRole)));
			return userRepository.save(user);
		} else {
			throw new Exception("The staff role not found");
		}
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

	@Override
	public boolean checkStaffUser(Team team) {
		return userRepository.findByEmail(team.getName()) != null;
	}

	@Override
	public Authentication getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ("true".equals(test) && authentication.getPrincipal().toString().equals("anonymousUser")) {
			UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken("nicola@nicola.it",
					"nicola");
			authentication = authenticationManager.authenticate(authReq);
		}
		return authentication;
	}

}
