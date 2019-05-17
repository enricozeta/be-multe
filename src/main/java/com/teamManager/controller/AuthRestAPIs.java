package com.teamManager.controller;

import java.util.Arrays;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamManager.dto.TeamDTO;
import com.teamManager.model.Role;
import com.teamManager.model.Team;
import com.teamManager.model.User;
import com.teamManager.repository.IRoleRepository;
import com.teamManager.repository.ITeamRepository;
import com.teamManager.repository.IUserRepository;
import com.teamManager.security.jwt.JwtProvider;
import com.teamManager.security.jwt.JwtResponse;
import com.teamManager.security.jwt.LoginForm;
import com.teamManager.security.jwt.SignUpForm;
import com.teamManager.service.TeamService;
import com.teamManager.service.UserService;

/**
 * The Class AuthRestAPIs.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private TeamService teamService;

	@Autowired
	private UserService userService;

	@Autowired
	private ITeamRepository teamRepository;

	@Autowired
	private JwtProvider jwtProvider;

	/**
	 * Authenticate user.
	 *
	 * @param loginRequest
	 *            the login request
	 * @return the response entity
	 */
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		return ResponseEntity.ok(new JwtResponse(jwt));
	}

	/**
	 * Register user.
	 *
	 * @param signUpRequest
	 *            the sign up request
	 * @return the response entity
	 * @throws Exception
	 */
	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) throws Exception {
		if (userRepository.findByEmail(signUpRequest.getEmail()) != null) {
			return new ResponseEntity<>("Fail -> Email is already in use!", HttpStatus.BAD_REQUEST);
		}
		TeamDTO teamDTO = new TeamDTO();
		teamDTO.setName(signUpRequest.getTeam());
		Team teamSaved = teamService.save(teamDTO);

		try {
			User user = new User();
			user.setEmail(signUpRequest.getEmail());
			user.setName(signUpRequest.getName());
			user.setLastName(signUpRequest.getSurname());
			user.setTeam(teamSaved);
			user.setPassword(signUpRequest.getPassword());
			Role userRole = roleRepository.findByRole("ADMIN");
			user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
			User created = userService.saveUser(user);
			teamSaved.setUser(created);
			teamRepository.save(teamSaved);
		} catch (Exception e) {
			teamRepository.delete(teamSaved);
		}
		// Creating user's account

		return ResponseEntity.ok().body("User registered successfully!");
	}
}
