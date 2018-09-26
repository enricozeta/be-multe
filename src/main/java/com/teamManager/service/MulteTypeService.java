package com.teamManager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamManager.model.MulteType;
import com.teamManager.repository.IMulteTypeRepository;

/**
 * The Class MulteTypeService.
 */
@Service("multeTypeService")
public class MulteTypeService {

	@Autowired
	private IMulteTypeRepository multeTypeRepository;

	@Autowired
	private TeamService teamService;

	/**
	 * Adds the.
	 *
	 * @param multaType
	 *            the multa type
	 * @return the multe type
	 * @throws Exception
	 *             the exception
	 */
	public MulteType add(MulteType multaType) throws Exception {
		if (multaType.getTeam() == null) {
			throw new Exception("This multa isn't have a team");
		}
		teamService.checkTeam(multaType.getTeam());
		return multeTypeRepository.save(multaType);
	}

	/**
	 * Gets the.
	 *
	 * @param id
	 *            the id
	 * @return the multe type
	 * @throws Exception
	 *             the exception
	 */
	public MulteType get(Long id) throws Exception {
		Optional<MulteType> findById = multeTypeRepository.findById(id);
		if (findById.isPresent()) {
			if (findById.get().getTeam() == null) {
				throw new Exception("This multa isn't have a team");
			}
			teamService.checkTeam(findById.get().getTeam());
			return findById.get();
		} else {
			return null;
		}
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 * @throws Exception
	 *             the exception
	 */
	public Iterable<MulteType> getAll() throws Exception {
		return teamService.getCurrentTeam().getMulteTypes();
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
	public boolean delete(Long id) throws Exception {
		MulteType multeType = this.get(id);
		multeTypeRepository.deleteById(multeType.getId());
		return true;
	}
}
