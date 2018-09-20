package com.teamManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamManager.model.Player;
import com.teamManager.model.Team;
import com.teamManager.model.User;
import com.teamManager.repository.IPlayerRepository;

/**
 * The Class PlayerService.
 */
@Service("playerService")
public class PlayerService {

	@Autowired
	private TeamService teamService = new TeamService();

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private IPlayerRepository playerRepository;

	/**
	 * Adds the.
	 *
	 * @param player
	 *            the player
	 * @return the player
	 * @throws Exception
	 *             the exception
	 */
	public Player add(Player player) throws Exception {
		teamService.checkTeam(player.getTeam());
		return playerRepository.save(player);
	}

	/**
	 * Update.
	 *
	 * @param player
	 *            the player
	 * @return the player
	 * @throws Exception
	 *             the exception
	 */
	public Player update(Player player) throws Exception {
		teamService.checkTeam(player.getTeam());
		return playerRepository.save(player);
	}

	/**
	 * Gets the player by id.
	 *
	 * @param id
	 *            the id
	 * @return the player by id
	 * @throws Exception
	 *             the exception
	 */
	public Player getPlayerById(Long id) throws Exception {
		Optional<Player> findById = playerRepository.findById(id);
		if (findById.isPresent()) {
			Team team = teamService.getCurrentTeam();
			List<Player> players = team.getPlayers();
			if (players.contains(findById.get())) {
				return findById.get();
			} else {
				throw new Exception("This player isn't in your team or not exist");
			}
		} else {
			return null;
		}
	}

	/**
	 * Gets the all player.
	 *
	 * @return the all player
	 * @throws Exception
	 *             the exception
	 */
	public List<Player> getAllPlayer() throws Exception {
		return teamService.getCurrentTeam().getPlayers();
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @throws Exception
	 *             the exception
	 */
	public void delete(Long id) throws Exception {
		Player playerById = this.getPlayerById(id);
		playerRepository.delete(playerById);
	}

}
