package com.teamManager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamManager.dto.PlayerHome;
import com.teamManager.model.Team;
import com.teamManager.model.User;
import com.teamManager.repository.ITeamRepository;
import com.teamManager.repository.IUserRepository;
import com.teamManager.service.TeamService;
import com.teamManager.service.UserService;

/**
 * The Class TeamController.
 */
@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TeamController {

	@Autowired
	private ITeamRepository teamRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private TeamService teamService = new TeamService();

	/**
	 * Adds the.
	 *
	 * @param team
	 *            the team
	 * @return the boolean
	 */
	@RequestMapping(value = { "admin/team" }, method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody Boolean add(@RequestBody Team team) {
		String email = userService.getAuthentication().getName();
		User user = userRepository.findByEmail(email);
		team.setUser(user);
		teamRepository.save(team);
		return true;
	}

	/**
	 * Update.
	 *
	 * @param team
	 *            the team
	 * @return the boolean
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "admin/team" }, method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody Boolean update(@RequestBody Team team) throws Exception {
		teamRepository.save(teamService.checkTeam(team));
		return true;
	}

	/**
	 * Gets the.
	 *
	 * @return the team
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "team" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody Team get() throws Exception {
		return teamService.getCurrentTeam();
	}

	/**
	 * Gets the.
	 *
	 * @param user
	 *            the user
	 * @return the user
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = "admin/createStaffUser", method = RequestMethod.POST)
	public @ResponseBody User createStaffUser(@Valid User user) throws Exception {
		return userService.createStaffUser(user);
	}

	/**
	 * Gets the.
	 *
	 * @return the team
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "admin/team" }, method = RequestMethod.DELETE, produces = { "application/json" })
	public @ResponseBody void delete() throws Exception {
		teamRepository.delete(teamService.getCurrentTeam());
	}

	/**
	 * Gets the worst.
	 *
	 * @return the worst
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "team/worst" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<PlayerHome> getWorst() throws Exception {
		return teamService.getWorst();
	}

	@RequestMapping(value = { "team/best" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<PlayerHome> getBest() throws Exception {
		return teamService.getBest();
	}
}
