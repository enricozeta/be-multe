package com.teamManager.repository;

import org.springframework.data.repository.CrudRepository;

import com.teamManager.model.Player;

/**
 * The Interface IPlayerRepository.
 */
public interface IPlayerRepository extends CrudRepository<Player, Long>{

}
