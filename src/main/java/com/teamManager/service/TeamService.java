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

import com.teamManager.adapter.TeamAdapterManager;
import com.teamManager.dto.PlayerDTO;
import com.teamManager.dto.PlayerHome;
import com.teamManager.dto.TeamDTO;
import com.teamManager.model.Multa;
import com.teamManager.model.Team;
import com.teamManager.model.User;
import com.teamManager.repository.ITeamRepository;
import com.teamManager.repository.IUserRepository;
import com.teamManager.security.jwt.UserPrinciple;

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

	@Autowired
	private TeamAdapterManager adapterManager;

	@Value("${test}")
	private String test;

	public Team save(TeamDTO team) throws Exception {
		return teamRepository.save(adapterManager.getAdapter(team, Team.class));
	}

	public Team save(Team team) {
		return teamRepository.save(team);
	}

	/**
	 * Gets the current team.
	 *
	 * @return the current team
	 * @throws Exception
	 *             the exception
	 */
	public TeamDTO getCurrentTeam() throws Exception {
		UserPrinciple userPrincipal = (UserPrinciple) userService.getAuthentication().getPrincipal();
		String email = userPrincipal.getEmail();
		User user = userRepository.findByEmail(email);
		Team team = teamRepository.findByUser(user);
		if (team == null) {
			throw new Exception("The current user don't have a team");
		}
		return adapterManager.getAdapter(team, TeamDTO.class);
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
	public Team checkTeam(@NonNull Long teamId) throws Exception {
		String email = userService.getAuthentication().getName();
		User findUserByEmail = userService.findUserByEmail(email);
		if (findUserByEmail.getTeam().getId().equals(teamId)) {
			return teamRepository.findById(teamId).get();
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
		Map<PlayerDTO, List<Multa>> result = getPlayerForHome();
		for (PlayerDTO player : result.keySet()) {
			double total = multeService.getTotal(result.get(player));
			worstPlayer.add(
					new PlayerHome(player.getId(), player.getName(), player.getSurname(), total, result.get(player)));
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
		Map<PlayerDTO, List<Multa>> result = getPlayerForHome();
		for (PlayerDTO player : result.keySet()) {
			double total = multeService.getTotal(result.get(player));
			bestPlayers.add(
					new PlayerHome(player.getId(), player.getName(), player.getSurname(), total, result.get(player)));
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

	/**
	 * Reset total.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void resetTotal() throws Exception {
		TeamDTO currentTeam = getCurrentTeam();
		List<PlayerDTO> players = currentTeam.getPlayers();
		double total = 0;
		for (PlayerDTO player : players) {
			total += player.getMultePagate();
		}
		currentTeam.setPaid(total);
	}

	private Map<PlayerDTO, List<Multa>> getPlayerForHome() throws Exception {
		Map<PlayerDTO, List<Multa>> result = new HashMap<>();
		TeamDTO team = this.getCurrentTeam();
		List<PlayerDTO> players = team.getPlayers();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date oneMonthAgo = cal.getTime();
		Date today = new Date();
		for (PlayerDTO player : players) {
			List<Multa> multeWithFilter = multeService.getMulteWithFilter(player, oneMonthAgo, today);
			result.put(player, multeWithFilter);
		}
		return result;

	}

}
