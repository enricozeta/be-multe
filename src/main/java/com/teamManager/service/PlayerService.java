package com.teamManager.service;

import java.util.ArrayList;
import java.util.List;

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

@Service("playerService")
public class PlayerService {

	@Autowired
	private TeamService teamService = new TeamService();

	@PersistenceContext
	private EntityManager entityManager;

	public Player add(Player player) throws Exception {
		player.setTeam(teamService.getCurrentTeam());
		return player;
	}

	public Player update(Player player) throws Exception {
		if (!player.getTeam().equals(teamService.getCurrentTeam())) {
			throw new Exception("This player isn't in your team");
		}
		return player;
	}

	public Player getPlayerById(Long id) throws Exception {
		Team team = teamService.getCurrentTeam();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Player> createQuery = criteriaBuilder.createQuery(Player.class);
		final Root<Player> root = createQuery.from(Player.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(criteriaBuilder.equal(root.<Long>get("id"), team.getId()));
		Join<Team, User> chairJoin = root.join("user_id", JoinType.INNER);
		predicates.add(chairJoin.get("id").in(id));

		createQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

		List<Player> resultList = entityManager.createQuery(createQuery).getResultList();

		if (resultList.isEmpty()) {
			throw new Exception("This player isn't in your team or not exist");
		}

		return resultList.get(0);
	}

	public List<Player> getAllPlayer() throws Exception {
		return teamService.getCurrentTeam().getPlayers();
	}

}
