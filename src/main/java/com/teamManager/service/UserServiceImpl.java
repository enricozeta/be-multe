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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamManager.adapter.TeamAdapterManager;
import com.teamManager.configuration.MailSenderConfig;
import com.teamManager.dto.TeamDTO;
import com.teamManager.dto.UserDTO;
import com.teamManager.model.Role;
import com.teamManager.model.Team;
import com.teamManager.model.User;
import com.teamManager.repository.IRoleRepository;
import com.teamManager.repository.IUserRepository;
import com.teamManager.security.jwt.UserPrinciple;

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

	@Autowired
	private TeamAdapterManager adapterManager;

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
	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		return userRepository.save(user);
	}

	@Override
	public User updateUser(UserDTO user) {
		User findByEmail = userRepository.findByEmail(user.getEmail());
		findByEmail.setName(user.getName());
		findByEmail.setLastName(user.getSurname());
		return userRepository.save(findByEmail);
	}

	@Override
	public User getStaffUser() throws Exception {
		TeamDTO currentTeam = teamService.getCurrentTeam();
		String name = currentTeam.getName();
		return userRepository.findByEmail(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teamManager.service.UserService#createStaffUser(com.teamManager.model
	 * .User)
	 */
	@Override
	public User createStaffUser(UserDTO userDTO) throws Exception {

		// set user's fields
		TeamDTO currentTeam = teamService.getCurrentTeam();
		String name = currentTeam.getName();
		User user = new User();
		user.setEmail(name);
		user.setName(name);
		user.setTeam(adapterManager.getAdapter(currentTeam, Team.class));
		user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
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
				if (this.getAuthentication().equals(email)) {
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
	public String getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ("true".equals(test) && authentication.getPrincipal().toString().equals("anonymousUser")) {
			UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken("nicola@nicola.it",
					"nicola");
			authentication = authenticationManager.authenticate(authReq);
		}
		com.teamManager.security.jwt.UserPrinciple userPrincipal = (com.teamManager.security.jwt.UserPrinciple) authentication
				.getPrincipal();
		return userPrincipal.getEmail();
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User Not Found with -> username or email : " + username);
		}

		return UserPrinciple.build(user);
	}

}
