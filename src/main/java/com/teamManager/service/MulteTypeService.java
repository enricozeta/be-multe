package com.teamManager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamManager.adapter.MulteTypeAdapter;
import com.teamManager.dto.MultaTypeDTO;
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
	private MulteTypeAdapter multaTypeAdapter;

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
	public MultaTypeDTO add(MultaTypeDTO multaType) throws Exception {
		if (multaType.getTeamId() == null) {
			throw new Exception("This multa isn't have a team");
		}
		teamService.checkTeam(multaType.getTeamId());
		MulteType save = multeTypeRepository.save(multaTypeAdapter.getAdapter(multaType, MulteType.class));
		return multaTypeAdapter.getAdapter(save, MultaTypeDTO.class);
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
	public MultaTypeDTO get(Long id) throws Exception {
		Optional<MulteType> findById = multeTypeRepository.findById(id);
		if (findById.isPresent()) {
			MulteType multeType = findById.get();
			if (multeType.getTeam() == null) {
				throw new Exception("This multa isn't have a team");
			}
			teamService.checkTeam(multeType.getTeam().getId());
			return multaTypeAdapter.getAdapter(multeType, MultaTypeDTO.class);
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
	public Iterable<MultaTypeDTO> getAll() throws Exception {
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
		this.get(id);
		multeTypeRepository.deleteById(id);
		return true;
	}
}
