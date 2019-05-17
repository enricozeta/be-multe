package com.teamManager.dto;

import java.util.ArrayList;
import java.util.List;

public class PlayerDTO {

	private Long id = null;

	private String name = null;

	private String surname = null;

	private List<MultaDTO> multe = new ArrayList<>();

	private Long teamId = null;

	private double multeNonPagate;

	private double multePagate;

	private Boolean enabled = true;

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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<MultaDTO> getMulte() {
		return multe;
	}

	public void setMulte(List<MultaDTO> multe) {
		this.multe = multe;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public double getMulteNonPagate() {
		return multeNonPagate;
	}

	public void setMulteNonPagate(double multeNonPagate) {
		this.multeNonPagate = multeNonPagate;
	}

	public double getMultePagate() {
		return multePagate;
	}

	public void setMultePagate(double multePagate) {
		this.multePagate = multePagate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setTotalMulte() {
		this.multePagate = 0;
		this.multeNonPagate = 0;
		for (MultaDTO multaDTO : this.getMulte()) {
			if (multaDTO.isPagata()) {
				this.multePagate += multaDTO.getValore();
			} else {
				this.multeNonPagate += multaDTO.getValore();
			}
		}
	}

}
