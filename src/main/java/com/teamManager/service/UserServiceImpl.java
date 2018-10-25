package com.teamManager.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.teamManager.configuration.MailSenderConfig;
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
	private MailSenderConfig mailSenderConfig;

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
	public boolean changePassword(@NonNull String email, @NonNull String oldPassword, @NonNull String newPassword,
			Boolean forgot) throws Exception {
		try {
			User user = this.findUserByEmail(email);
			JavaMailSender javaMailSender = mailSenderConfig.getJavaMailSender();
			SimpleMailMessage message = new SimpleMailMessage();
			if (forgot) {
				user.setPassword(bCryptPasswordEncoder.encode("nicola"));
				message.setTo(email);
				message.setFrom("enrico_04@hotmail.it");
				message.setSubject("Reset Password");
				message.setText("La tua nuova password è temp1234567temp. Ricordati di modificarla");
			} else {
				if (this.getAuthentication().getName().equals(email)) {
					if (bCryptPasswordEncoder.matches(user.getPassword(), oldPassword)) {
						user.setPassword(bCryptPasswordEncoder.encode(newPassword));
					} else {
						throw new Exception("Password update incomplete");
					}
				} else {
					throw new Exception("Forbidden");
				}
			}
			userRepository.save(user);
			if (forgot) {
				javaMailSender.send(message);
			}
			return true;
		} catch (Exception e) {
			throw new Exception("Password update incomplete." + e.getMessage());
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
