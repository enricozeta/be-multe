package com.teamManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.teamManager.model.Multa;
import com.teamManager.model.Player;
import com.teamManager.repository.IMultaRepository;

/**
 * The Class MulteService.
 */
@Service("multeService")
public class MulteService {

	@Autowired
	private PlayerService playerService = new PlayerService();

	@Autowired
	private TeamService teamService = new TeamService();

	@Autowired
	private IMultaRepository multaRepository;

	/**
	 * Adds the update.
	 *
	 * @param multa
	 *            the multa
	 * @return the multa
	 * @throws Exception
	 *             the exception
	 */
	public Multa addUpdate(@NonNull Multa multa) throws Exception {
		if (multa.getPlayer() != null && playerService.getPlayerById(multa.getPlayer().getId()) != null) {
			return multaRepository.save(multa);
		} else {
			throw new Exception("This multa is invalid");
		}
	}

	/**
	 * Gets the all of team.
	 *
	 * @return the all of team
	 * @throws Exception
	 *             the exception
	 */
	public List<Multa> getAllOfTeam() throws Exception {
		List<Player> players = teamService.getCurrentTeam().getPlayers();
		List<Multa> result = new ArrayList<>();
		for (Player player : players) {
			result.addAll(player.getMulte());
		}
		return result;
	}

	/**
	 * Gets the by id.
	 *
	 * @param id
	 *            the id
	 * @return the by id
	 * @throws Exception
	 *             the exception
	 */
	public Optional<Multa> getById(@NonNull Long id) throws Exception {
		try {
			Optional<Multa> multa = multaRepository.findById(id);
			playerService.getPlayerById(multa.get().getPlayer().getId());
			return multa;
		} catch (Exception e) {
			throw new Exception("This multa isn't in your team");
		}
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public boolean delete(@NonNull Long id) throws Exception {
		Optional<Multa> multa = multaRepository.findById(id);
		if (multa.get().getPlayer() != null && playerService.getPlayerById(multa.get().getPlayer().getId()) != null) {
			multaRepository.deleteById(id);
			return true;
		} else {
			throw new Exception("This multa isn't in your team");
		}
	}

}
