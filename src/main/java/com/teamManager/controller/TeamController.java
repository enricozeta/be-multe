package com.teamManager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamManager.model.Team;
import com.teamManager.model.User;
import com.teamManager.repository.ITeamRepository;
import com.teamManager.service.TeamService;
import com.teamManager.service.UserService;

@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TeamController {

	@Autowired
	private ITeamRepository teamRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private TeamService teamService = new TeamService();

	@RequestMapping(value = { "admin/team" }, method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody Boolean add(@RequestBody Team team) {
		teamRepository.save(team);
		return true;
	}

	@RequestMapping(value = { "admin/team" }, method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody Boolean update(@RequestBody Team team) throws Exception {
		teamRepository.save(teamService.checkTeam(team));
		return true;
	}

	@RequestMapping(value = { "admin/team" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody Team get() throws Exception {
		return teamService.getCurrentTeam();
	}

	@RequestMapping(value = "admin/createStaffUser", method = RequestMethod.POST)
	public @ResponseBody User get(@Valid User user) throws Exception {
		return userService.createStaffUser(user);
	}

}
