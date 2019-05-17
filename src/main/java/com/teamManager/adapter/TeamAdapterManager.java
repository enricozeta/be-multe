package com.teamManager.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.teamManager.dto.MultaTypeDTO;
import com.teamManager.dto.PlayerDTO;
import com.teamManager.dto.TeamDTO;
import com.teamManager.model.MulteType;
import com.teamManager.model.Player;
import com.teamManager.model.Team;
import com.teamManager.model.User;
import com.teamManager.repository.IUserRepository;

/**
 * The Class TeamAdapterManager.
 */
@Service
public class TeamAdapterManager implements AdapterService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private MulteTypeAdapter multaAdapter;

	@Autowired
	private PlayerAdapterManager playerAdapter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamManager.adapter.AdapterService#getAdapter(java.lang.Object,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) throws Exception {
		if (adaptableObject instanceof Team && adapterType == TeamDTO.class) {
			return (T) getDTO((Team) adaptableObject);
		}
		if (adaptableObject instanceof TeamDTO && adapterType == Team.class) {
			return (T) getEntity((TeamDTO) adaptableObject);
		}
		return null;
	}

	@NonNull
	private TeamDTO getDTO(Team entity) throws Exception {
		TeamDTO dto = new TeamDTO();
		dto.setId(entity.getId());
		dto.setMulteTypes(getMulteTypeDTOs(entity));
		dto.setName(entity.getName());
		dto.setNoPaid(entity.getNoPaid());
		dto.setPaid(entity.getPaid());
		dto.setPlayers(getPlayerDTOs(entity));
		dto.setUserId(entity.getUser().getId());
		dto.setFondoCassa(entity.getFondoCassa());
		setPaidNoPaid(dto);
		return dto;
	}

	@NonNull
	private Team getEntity(TeamDTO dto) throws Exception {
		Team entity = new Team();
		Long id = dto.getId();
		if (id != null) {
			entity.setId(id);
		}
		entity.setMulteTypes(getMulteTypeDTOs(dto));
		entity.setName(dto.getName());
		entity.setPlayers(getPlayerDTOs(dto));
		entity.setFondoCassa(dto.getFondoCassa());

		if (dto.getUserId() != null) {
			Optional<User> user = userRepository.findById(dto.getUserId());
			entity.setUser(user.isPresent() ? user.get() : null);
		}

		return entity;
	}

	private List<MultaTypeDTO> getMulteTypeDTOs(Team entity) throws Exception {
		List<MultaTypeDTO> result = new ArrayList<>();
		List<MulteType> multeTypes = entity.getMulteTypes();
		if (multeTypes != null) {
			for (MulteType multaType : multeTypes) {
				result.add(multaAdapter.getAdapter(multaType, MultaTypeDTO.class));
			}
		}
		return result;
	}

	private List<MulteType> getMulteTypeDTOs(TeamDTO dto) throws Exception {
		List<MulteType> result = new ArrayList<>();
		List<MultaTypeDTO> multeTypes = dto.getMulteTypes();
		if (multeTypes != null) {
			for (MultaTypeDTO multaType : multeTypes) {
				result.add(multaAdapter.getAdapter(multaType, MulteType.class));
			}
		}
		return result;
	}

	private List<PlayerDTO> getPlayerDTOs(Team entity) throws Exception {
		List<PlayerDTO> result = new ArrayList<>();
		List<Player> players = entity.getPlayers();
		if (players != null) {
			for (Player player : players) {
				result.add(playerAdapter.getAdapter(player, PlayerDTO.class));
			}
		}
		return result;
	}

	private List<Player> getPlayerDTOs(TeamDTO dto) throws Exception {
		List<Player> result = new ArrayList<>();
		List<PlayerDTO> players = dto.getPlayers();
		if (players != null) {
			for (PlayerDTO player : players) {
				result.add(playerAdapter.getAdapter(player, Player.class));
			}
		}
		return result;
	}

	private void setPaidNoPaid(TeamDTO team) {
		for (PlayerDTO playerDTO : team.getPlayers()) {
			team.setPaid(team.getPaid() + playerDTO.getMultePagate());
			team.setNoPaid(team.getNoPaid() + playerDTO.getMulteNonPagate());
		}
		team.setPaid(team.getPaid() + team.getFondoCassa());
	}

}
