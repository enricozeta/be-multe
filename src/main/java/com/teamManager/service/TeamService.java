package com.teamManager.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.teamManager.adapter.TeamAdapterManager;
import com.teamManager.dto.CharDataDTO;
import com.teamManager.dto.PlayerDTO;
import com.teamManager.dto.TeamDTO;
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
		String email = userService.getAuthentication();
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
		String email = userService.getAuthentication();
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
	public List<PlayerDTO> getWorst() throws Exception {
		List<PlayerDTO> players = this.getCurrentTeam().getPlayers();
		players.sort(new Comparator<PlayerDTO>() {
			@Override
			public int compare(PlayerDTO p1, PlayerDTO p2) {
				return (int) ((p1.getMultePagate() + p1.getMulteNonPagate())
						- (p2.getMultePagate() + p2.getMulteNonPagate())) * (-1);
			}

		});
		return players;
	}

	/**
	 * Gets the best.
	 *
	 * @return the worst
	 * @throws Exception
	 *             the exception
	 */
	public List<PlayerDTO> getBest() throws Exception {
		List<PlayerDTO> players = this.getCurrentTeam().getPlayers();
		players.sort(new Comparator<PlayerDTO>() {
			@Override
			public int compare(PlayerDTO p1, PlayerDTO p2) {
				return (int) ((p1.getMultePagate() + p1.getMulteNonPagate())
						- (p2.getMultePagate() + p2.getMulteNonPagate()));
			}

		});
		return players;
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

	public List<CharDataDTO> getChartData() throws Exception {
		List<CharDataDTO> result = new ArrayList<>();
		List<Tuple> chartData = teamRepository.getChartData(String.valueOf(this.getCurrentTeam().getId()));
		for (int i = 0; i < chartData.size(); i++) {
			Tuple item = chartData.get(i);
			if (i != 0) {
				CharDataDTO prec = result.get(i - 1);
				result.add(new CharDataDTO(item.get(0, Integer.class), item.get(1, Integer.class),
						item.get(2, Double.class) + prec.getValue()));
			} else {
				result.add(new CharDataDTO(item.get(0, Integer.class), item.get(1, Integer.class),
						item.get(2, Double.class)));
			}

		}
		result.sort(Comparator.comparing(CharDataDTO::getSort));
		return result;
	}

}
