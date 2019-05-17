package com.teamManager.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamManager.dto.MultaDTO;
import com.teamManager.dto.PlayerDTO;
import com.teamManager.model.Multa;
import com.teamManager.model.Player;
import com.teamManager.model.Team;
import com.teamManager.repository.ITeamRepository;

/**
 * The Class PlayerAdapterManager.
 */
@Service
public class PlayerAdapterManager implements AdapterService {

	@Autowired
	private ITeamRepository teamRepository;

	@Autowired
	private MultaAdpterManager multaAdapter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamManager.adapter.AdapterService#getAdapter(java.lang.Object,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) throws Exception {
		if (adaptableObject instanceof Player && adapterType == PlayerDTO.class) {
			return (T) getDTO((Player) adaptableObject);
		}
		if (adaptableObject instanceof PlayerDTO && adapterType == Player.class) {
			return (T) getEntity((PlayerDTO) adaptableObject);
		}
		return null;
	}

	private Player getEntity(PlayerDTO dto) throws Exception {
		Player entity = new Player();
		Long id = dto.getId();
		if (id != null) {
			entity.setId(id);
		}
		entity.setMulte(getMulteDTOs(dto));
		entity.setName(dto.getName());
		entity.setSurname(dto.getSurname());
		entity.setEnabled(dto.getEnabled());
		Optional<Team> team = teamRepository.findById(dto.getTeamId());
		entity.setTeam(team.isPresent() ? team.get() : null);
		return entity;
	}

	private PlayerDTO getDTO(Player entity) throws Exception {
		PlayerDTO dto = new PlayerDTO();
		dto.setId(entity.getId());
		dto.setMulte(getMulteDTOs(entity));
		this.setPaidNoPaid(dto);
		dto.setName(entity.getName());
		dto.setSurname(entity.getSurname());
		dto.setTeamId(entity.getTeam().getId());
		dto.setEnabled(entity.getEnabled());
		return dto;
	}

	private List<MultaDTO> getMulteDTOs(Player entity) throws Exception {
		List<MultaDTO> result = new ArrayList<>();
		List<Multa> multe = entity.getMulte();
		if (multe != null) {
			for (Multa multaType : multe) {
				result.add(multaAdapter.getAdapter(multaType, MultaDTO.class));
			}
		}
		return result;
	}

	private List<Multa> getMulteDTOs(PlayerDTO dto) throws Exception {
		List<Multa> result = new ArrayList<>();
		List<MultaDTO> multe = dto.getMulte();
		if (multe != null) {
			for (MultaDTO multaType : multe) {
				result.add(multaAdapter.getAdapter(multaType, Multa.class));
			}
		}
		return result;
	}

	private void setPaidNoPaid(PlayerDTO dto) {
		for (MultaDTO multaDTO : dto.getMulte()) {
			if (multaDTO.isPagata()) {
				dto.setMultePagate(dto.getMultePagate() + multaDTO.getValore());
			} else {
				dto.setMulteNonPagate(dto.getMulteNonPagate() + multaDTO.getValore());
			}
		}

	}
}
