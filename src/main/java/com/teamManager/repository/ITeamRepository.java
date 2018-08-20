package com.teamManager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import com.teamManager.model.Team;
import com.teamManager.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITeamRepository.
 */
public interface ITeamRepository extends CrudRepository<Team, Long> {

	/**
	 * Find by user.
	 *
	 * @param user the user
	 * @return the team
	 */
	public Team findByUser(@NonNull User user);
}
