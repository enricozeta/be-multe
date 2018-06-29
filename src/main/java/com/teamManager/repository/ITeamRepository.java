package com.teamManager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import com.teamManager.model.Team;
import com.teamManager.model.User;

public interface ITeamRepository extends CrudRepository<Team, Long> {

	public Team findByUser(@NonNull User user);
}
