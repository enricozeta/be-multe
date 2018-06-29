package com.teamManager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String surname;

	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Multa> multe = new ArrayList<Multa>();

	@ManyToOne
	@JoinColumn(name = "team_id")
	@JsonBackReference
	private Team team;

	private double multeNonPagate;

	private double multePagate;

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

	public List<Multa> getMulte() {
		return multe;
	}

	public void setMulte(List<Multa> multe) {
		this.multe = multe;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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

}
