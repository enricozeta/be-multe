package com.teamManager.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
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

	@Value("${test}")
	private String test;

	/**
	 * Gets the current team.
	 *
	 * @return the current team
	 * @throws Exception
	 *             the exception
	 */
	public Team getCurrentTeam() throws Exception {
		String email = userService.getAuthentication().getName();
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
		String email = userService.getAuthentication().getName();
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
	public List<PlayerHome> getWorst() throws Exception {
		List<PlayerHome> worstPlayer = new ArrayList<>();
		Map<Player, Double> result = getPlayerForHome();
		for (Player player : result.keySet()) {
			worstPlayer.add(new PlayerHome(player.getId(), player.getName(), player.getSurname(), result.get(player)));
		}
		Collections.sort(worstPlayer, new Comparator<PlayerHome>() {
			@Override
			public int compare(PlayerHome p1, PlayerHome p2) {
				return -(p1.getTotal().compareTo(p2.getTotal()));
			}
		});
		if (worstPlayer.size() > 5) {
			return worstPlayer.subList(0, 5);
		}
		return worstPlayer;
	}

	/**
	 * Gets the best.
	 *
	 * @return the worst
	 * @throws Exception
	 *             the exception
	 */
	public List<PlayerHome> getBest() throws Exception {
		List<PlayerHome> bestPlayers = new ArrayList<>();
		Map<Player, Double> result = getPlayerForHome();
		for (Player player : result.keySet()) {
			bestPlayers.add(new PlayerHome(player.getId(), player.getName(), player.getSurname(), result.get(player)));
		}
		Collections.sort(bestPlayers, new Comparator<PlayerHome>() {
			@Override
			public int compare(PlayerHome p1, PlayerHome p2) {
				return p1.getTotal().compareTo(p2.getTotal());
			}
		});
		if (bestPlayers.size() > 5) {
			return bestPlayers.subList(0, 5);
		}
		return bestPlayers;
	}

	private Map<Player, Double> getPlayerForHome() throws Exception {
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
		return result;

	}

}
