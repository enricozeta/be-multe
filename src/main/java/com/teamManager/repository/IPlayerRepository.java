package com.teamManager.repository;

import org.springframework.data.repository.CrudRepository;

import com.teamManager.model.Player;

public interface IPlayerRepository extends CrudRepository<Player, Long>{

}
