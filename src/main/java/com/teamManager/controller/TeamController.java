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

import com.teamManager.adapter.TeamAdapterManager;
import com.teamManager.dto.PlayerHome;
import com.teamManager.dto.TeamDTO;
import com.teamManager.dto.UserDTO;
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
	private TeamService teamService;

	@Autowired
	private TeamAdapterManager adapterManager;

	/**
	 * Adds the.
	 *
	 * @param team
	 *            the team
	 * @return the boolean
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "admin/team" }, method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody Boolean add(@RequestBody TeamDTO team) throws Exception {
		String email = userService.getAuthentication();
		User user = userRepository.findByEmail(email);
		team.setUserId(user.getId());
		teamRepository.save(adapterManager.getAdapter(team, Team.class));
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
	public @ResponseBody Boolean update(@RequestBody TeamDTO team) throws Exception {
		teamRepository.save(teamService.checkTeam(team.getId()));
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
	public @ResponseBody TeamDTO get() throws Exception {
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
	@RequestMapping(value = "admin/staffUser", method = RequestMethod.POST)
	public @ResponseBody User createStaffUser(@Valid UserDTO user) throws Exception {
		return userService.createStaffUser(user);
	}

	/**
	 * Gets the staff user.
	 *
	 * @return the staff user
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "staffUser" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody User getStaffUser() throws Exception {
		return userService.getStaffUser();
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
		teamRepository.delete(adapterManager.getAdapter(teamService.getCurrentTeam(), Team.class));
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

	/**
	 * Gets the best.
	 *
	 * @return the best
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "team/best" }, method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<PlayerHome> getBest() throws Exception {
		return teamService.getBest();
	}

	/**
	 * Reset total.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = { "admin/team/resetTotal" }, method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody void resetTotal() throws Exception {
		teamService.resetTotal();
	}
}
