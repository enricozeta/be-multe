package com.teamManager.adapter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.teamManager.dto.MultaDTO;
import com.teamManager.model.Multa;
import com.teamManager.model.MulteType;
import com.teamManager.model.Player;
import com.teamManager.repository.IMulteTypeRepository;
import com.teamManager.repository.IPlayerRepository;

/**
 * The Class MultaAdpterManager.
 */
@Service
public class MultaAdpterManager implements AdapterService {

	@Autowired
	private IMulteTypeRepository iMulteTypeRepository;

	@Autowired
	private IPlayerRepository iPlayerRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamManager.adapter.AdapterService#getAdapter(java.lang.Object,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) throws Exception {
		if (adaptableObject instanceof Multa && adapterType == MultaDTO.class) {
			return (T) getDTO((Multa) adaptableObject);
		}
		if (adaptableObject instanceof MultaDTO && adapterType == Multa.class) {
			return (T) getEntity((MultaDTO) adaptableObject);
		}
		return null;
	}

	@NonNull
	private MultaDTO getDTO(Multa entity) {
		MultaDTO dto = new MultaDTO();
		dto.setId(entity.getId());
		dto.setData(entity.getData());
		dto.setDescrizione(entity.getDescrizione());
		dto.setPagata(entity.isPagata());
		dto.setPlayer(entity.getPlayer().getId());
		dto.setValore(entity.getValore());
		dto.setMulteTypeId(entity.getMulteType().getId());

		return dto;
	}

	@NonNull
	private Multa getEntity(MultaDTO dto) throws Exception {
		Multa entity = new Multa();
		Long id = dto.getId();
		if (id != null) {
			entity.setId(id);
		}

		entity.setData(dto.getData());
		entity.setDescrizione(dto.getDescrizione());

		Optional<MulteType> multaType = iMulteTypeRepository.findById(dto.getmulteTypeId());
		entity.setMulteType(multaType.isPresent() ? multaType.get() : null);

		entity.setPagata(dto.isPagata());

		Optional<Player> player = iPlayerRepository.findById(dto.getPlayerId());
		entity.setPlayer(player.isPresent() ? player.get() : null);

		entity.setValore(dto.getValore());

		return entity;
	}

}
