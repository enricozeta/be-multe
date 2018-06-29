package com.teamManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.teamManager.model.Team;
import com.teamManager.model.User;
import com.teamManager.repository.ITeamRepository;
import com.teamManager.repository.IUserRepository;

@Service("teamService")
public class TeamService {

	@Autowired
	private ITeamRepository teamRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private IUserRepository userRepository;

	public Team getCurrentTeam() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email);
		Team team = teamRepository.findByUser(user);
		if (team == null) {
			throw new Exception("The current user don't have a team");
		}
		return team;
	}

	public Team checkTeam(@NonNull Team team) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User findUserByEmail = userService.findUserByEmail(email);
		Long teamId = findUserByEmail.getTeam().getId();
		if (team.getId().equals(teamId)) {
			return team;
		} else {
			throw new Exception("Forbidden");
		}
	}
}
