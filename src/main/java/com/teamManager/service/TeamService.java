package com.teamManager.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.teamManager.dto.PlayerHome;
import com.teamManager.model.Player;
import com.teamManager.model.Team;
import com.teamManager.model.User;
import com.teamManager.repository.ITeamRepository;
import com.teamManager.repository.IUserRepository;

/**
 * The Class TeamService.
 */
@Service("teamService")
public class TeamService {

	@Autowired
	private ITeamRepository teamRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private MulteService multeService;

	@Autowired
	private IUserRepository userRepository;

	/**
	 * Gets the current team.
	 *
	 * @return the current team
	 * @throws Exception
	 *             the exception
	 */
	public Team getCurrentTeam() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email);
		Team team = teamRepository.findByUser(user);
		if (team == null) {
			throw new Exception("The current user don't have a team");
		}
		team.setPaid();
		team.setNoPaid();
		return team;
	}

	/**
	 * Check team.
	 *
	 * @param team
	 *            the team
	 * @return the team
	 * @throws Exception
	 *             the exception
	 */
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

	/**
	 * Gets the worst.
	 *
	 * @return the worst
	 * @throws Exception
	 *             the exception
	 */
	public Set<PlayerHome> getWorst() throws Exception {
		Set<PlayerHome> worstPlayer = new HashSet<>();
		Map<Player, Double> result = new HashMap<>();
		Team team = this.getCurrentTeam();
		List<Player> players = team.getPlayers();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date oneMonthAgo = cal.getTime();
		Date today = new Date();
		for (Player player : players) {
			double total = multeService.getTotal(multeService.getMulteWithFilter(player, oneMonthAgo, today));
			result.put(player, total);
		}
		Map<Player, Double> collect = result.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		;
		for (Player player : collect.keySet()) {
			worstPlayer.add(new PlayerHome(player.getId(), player.getName(), player.getSurname(), result.get(player)));
		}
		return worstPlayer;
	}

}
