package com.teamManager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	@PersistenceContext
	private EntityManager entityManager;

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
		Player player = playerService.getPlayerById(multa.getPlayer().getId());
		if (multa.getPlayer() != null && player != null) {
			Multa result = multaRepository.save(multa);
			if (result.isPagata()) {
				player.setMultePagate(player.getMultePagate() + result.getValore());
			} else {
				player.setMulteNonPagate(player.getMulteNonPagate() + result.getValore());
			}
			playerService.update(player);
			return result;
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
		Player player = playerService.getPlayerById(multa.get().getPlayer().getId());
		if (multa.isPresent() && player != null) {
			Multa result = multa.get();
			if (result.isPagata()) {
				player.setMultePagate(player.getMultePagate() - result.getValore());
			} else {
				player.setMulteNonPagate(player.getMulteNonPagate() - result.getValore());
			}
			playerService.update(player);
			multaRepository.deleteById(result.getId());
			return true;
		} else {
			throw new Exception("This multa isn't in your team");
		}
	}

	/**
	 * Gets the multe with filter.
	 *
	 * @param player
	 *            the player
	 * @param oneMonthAgo
	 *            the one month ago
	 * @param today
	 *            the today
	 * @return the multe with filter
	 */
	public List<Multa> getMulteWithFilter(Player player, Date oneMonthAgo, Date today) {
		Query query = entityManager.createQuery("FROM Multa as m WHERE m.player = ? AND m.data <= ? AND m.data >= ?");
		query.setParameter(0, player);
		query.setParameter(1, today);
		query.setParameter(2, oneMonthAgo);
		return query.getResultList();
	}

	/**
	 * Gets the total.
	 *
	 * @param multe
	 *            the multe
	 * @return the total
	 */
	public double getTotal(List<Multa> multe) {
		double result = 0;
		for (Multa multa : multe) {
			result += multa.getValore();
		}
		return result;
	}

}
