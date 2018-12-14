package com.teamManager.adapter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.teamManager.dto.MultaTypeDTO;
import com.teamManager.model.MulteType;
import com.teamManager.model.Team;
import com.teamManager.repository.ITeamRepository;

/**
 * The Class MulteTypeAdapter.
 */
@Service
public class MulteTypeAdapter implements AdapterService {

	@Autowired
	private ITeamRepository teamRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamManager.adapter.AdapterService#getAdapter(java.lang.Object,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) throws Exception {
		if (adaptableObject instanceof MulteType && adapterType == MultaTypeDTO.class) {
			return (T) getDTO((MulteType) adaptableObject);
		}
		if (adaptableObject instanceof MultaTypeDTO && adapterType == MulteType.class) {
			return (T) getEntity((MultaTypeDTO) adaptableObject);
		}
		return null;
	}

	@NonNull
	private MultaTypeDTO getDTO(MulteType entity) {
		MultaTypeDTO dto = new MultaTypeDTO();
		dto.setId(entity.getId());
		dto.setDescrizione(entity.getDescrizione());
		dto.setTeamId(entity.getTeam().getId());
		dto.setValore(entity.getValore());

		return dto;
	}

	@NonNull
	private MulteType getEntity(MultaTypeDTO dto) throws Exception {
		MulteType entity = new MulteType();
		Long id = dto.getId();
		if (id != null) {
			entity.setId(id);
		}
		entity.setDescrizione(dto.getDescrizione());
		Optional<Team> team = teamRepository.findById(dto.getTeamId());
		entity.setTeam(team.isPresent() ? team.get() : null);
		entity.setValore(dto.getValore());

		return entity;
	}

}
