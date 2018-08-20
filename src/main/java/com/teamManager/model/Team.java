package com.teamManager.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The Class Team.
 */
@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Player> players;

	@Transient
	private double paid;

	@Transient
	private double NoPaid;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Sets the players.
	 *
	 * @param players
	 *            the new players
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	/**
	 * Gets the paid.
	 *
	 * @return the paid
	 */
	public double getPaid() {
		return this.paid;
	}

	/**
	 * Sets the paid.
	 */
	public void setPaid() {
		for (Player player : this.players) {
			this.paid = this.getPaid() + player.getMultePagate();
		}
	}

	/**
	 * Gets the no paid.
	 *
	 * @return the no paid
	 */
	public double getNoPaid() {
		return this.NoPaid;
	}

	/**
	 * Sets the no paid.
	 */
	public void setNoPaid() {
		for (Player player : this.players) {
			this.NoPaid = this.getNoPaid() + player.getMulteNonPagate();
		}
	}

}
