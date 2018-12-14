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

import com.teamManager.adapter.MultaAdpterManager;
import com.teamManager.adapter.PlayerAdapterManager;
import com.teamManager.dto.MultaDTO;
import com.teamManager.dto.PlayerDTO;
import com.teamManager.model.Multa;
import com.teamManager.model.Player;
import com.teamManager.repository.IMultaRepository;

/**
 * The Class MulteService.
 */
@Service("multeService")
public class MulteService {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private IMultaRepository multaRepository;

	@PersistenceContext
	private EntityManager entityManager;

	private static final String MULTA_ERROR = "This multa isn't in your team";

	@Autowired
	private MultaAdpterManager adpterManager;

	@Autowired
	private PlayerAdapterManager playerAdapterManager;

	/**
	 * Adds the update.
	 *
	 * @param multa
	 *            the multa
	 * @return the multa
	 * @throws Exception
	 *             the exception
	 */
	public MultaDTO addUpdate(@NonNull MultaDTO multa) throws Exception {
		PlayerDTO player = playerService.getPlayerById(multa.getPlayerId());
		if (player != null) {
			Multa result = multaRepository.save(adpterManager.getAdapter(multa, Multa.class));
			return adpterManager.getAdapter(result, MultaDTO.class);
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
	public List<MultaDTO> getAllOfTeam() throws Exception {
		List<PlayerDTO> players = teamService.getCurrentTeam().getPlayers();
		List<MultaDTO> result = new ArrayList<>();
		for (PlayerDTO player : players) {
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
	public MultaDTO getById(@NonNull Long id) throws Exception {
		try {
			Optional<Multa> multa = multaRepository.findById(id);
			if (!multa.isPresent()) {
				throw new Exception(MULTA_ERROR);
			}
			Multa result = multa.get();
			teamService.checkTeam(multa.get().getPlayer().getTeam().getId());
			return adpterManager.getAdapter(result, MultaDTO.class);
		} catch (Exception e) {
			throw new Exception(MULTA_ERROR);
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
		this.getById(id);
		multaRepository.deleteById(id);
		return true;
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
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Multa> getMulteWithFilter(PlayerDTO player, Date oneMonthAgo, Date today) throws Exception {
		Query query = entityManager.createQuery("FROM Multa as m WHERE m.player = ? AND m.data <= ? AND m.data >= ?");
		query.setParameter(0, playerAdapterManager.getAdapter(player, Player.class));
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
