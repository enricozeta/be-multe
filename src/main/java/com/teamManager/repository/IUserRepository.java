package com.teamManager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import com.teamManager.model.User;

public interface IUserRepository extends CrudRepository<User, Long> {

	public User findByEmail(@NonNull String email);
}
