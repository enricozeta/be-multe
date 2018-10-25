package com.teamManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teamManager.model.DBFile;

/**
 * The Interface IDBFileRepository.
 */
@Repository
public interface IDBFileRepository extends JpaRepository<DBFile, String> {

}
