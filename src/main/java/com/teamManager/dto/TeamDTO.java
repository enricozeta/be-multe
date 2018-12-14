package com.teamManager.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;

public class TeamDTO {

	private Long id = null;

	@NonNull
	private String name = null;

	private List<PlayerDTO> players = new ArrayList<>();

	private double paid;

	private double NoPaid;

	@NonNull
	private Long userId;

	private List<MultaTypeDTO> multeTypes = new ArrayList<>();

	private double fondoCassa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PlayerDTO> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerDTO> players) {
		this.players = players;
	}

	public double getPaid() {
		return paid;
	}

	public void setPaid(double paid) {
		this.paid = paid;
	}

	public double getNoPaid() {
		return NoPaid;
	}

	public void setNoPaid(double noPaid) {
		NoPaid = noPaid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<MultaTypeDTO> getMulteTypes() {
		return multeTypes;
	}

	public void setMulteTypes(List<MultaTypeDTO> multeTypes) {
		this.multeTypes = multeTypes;
	}

	public double getFondoCassa() {
		return fondoCassa;
	}

	public void setFondoCassa(double fondoCassa) {
		this.fondoCassa = fondoCassa;
	}

}
