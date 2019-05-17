package com.teamManager.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import com.teamManager.model.Team;
import com.teamManager.model.User;

/**
 * The Interface ITeamRepository.
 */
public interface ITeamRepository extends CrudRepository<Team, Long> {

	/**
	 * Find by user.
	 *
	 * @param user
	 *            the user
	 * @return the team
	 */
	public Team findByUser(@NonNull User user);

	@Query(value = " select extract(MONTH from `data`), extract(YEAR from `data`), SUM(valore) from multa a where a.player_id IN (select p.id from player p where p.team_id = ?#{[0]}) and a.pagata = true group by extract(MONTH from `data`), extract(YEAR from `data`) order by extract(YEAR from `data`) ASC, extract(MONTH from `data`) ASC ", nativeQuery = true)
	public List<Tuple> checkTypeOfCharData(String team_id);

	@Query(value = " select extract(MONTH from `data`), extract(YEAR from `data`), SUM(valore) from multa a where a.player_id IN (select p.id from player p where p.team_id = ?#{[0]}) and a.pagata = true group by extract(MONTH from `data`), extract(YEAR from `data`) order by extract(YEAR from `data`) ASC, extract(MONTH from `data`) ASC ", nativeQuery = true)
	public List<Tuple> getChartDataByMonth(String team_id);

	@Query(value = " select extract(DAY from `data`), extract(MONTH from `data`), extract(YEAR from `data`), SUM(valore) from multa a where a.player_id IN (select p.id from player p where p.team_id = ?#{[0]}) and a.pagata = true group by extract(DAY from `data`), extract(MONTH from `data`), extract(YEAR from `data`) order by extract(YEAR from `data`) ASC, extract(MONTH from `data`) ASC ", nativeQuery = true)
	public List<Tuple> getChartDataByDay(String team_id);
	
	@Query(value = " select extract(MONTH from `data`), extract(YEAR from `data`), SUM(valore) from multa a where a.player_id IN (select p.id from player p where p.team_id = ?#{[0]}) and a.pagata = false group by extract(MONTH from `data`), extract(YEAR from `data`) order by extract(YEAR from `data`) ASC, extract(MONTH from `data`) ASC ", nativeQuery = true)
	public List<Tuple> checkTypeOfCharDataNoPay(String team_id);

	@Query(value = " select extract(MONTH from `data`), extract(YEAR from `data`), SUM(valore) from multa a where a.player_id IN (select p.id from player p where p.team_id = ?#{[0]}) and a.pagata = false group by extract(MONTH from `data`), extract(YEAR from `data`) order by extract(YEAR from `data`) ASC, extract(MONTH from `data`) ASC ", nativeQuery = true)
	public List<Tuple> getChartDataByMonthNoPay(String team_id);

	@Query(value = " select extract(DAY from `data`), extract(MONTH from `data`), extract(YEAR from `data`), SUM(valore) from multa a where a.player_id IN (select p.id from player p where p.team_id = ?#{[0]}) and a.pagata = false group by extract(DAY from `data`), extract(MONTH from `data`), extract(YEAR from `data`) order by extract(YEAR from `data`) ASC, extract(MONTH from `data`) ASC ", nativeQuery = true)
	public List<Tuple> getChartDataByDayNoPay(String team_id);

}
