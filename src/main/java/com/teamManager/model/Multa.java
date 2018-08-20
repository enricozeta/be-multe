package com.teamManager.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The Class Multa.
 */
@Entity
public class Multa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descrizione;

	private double valore;

	private Date data;

	private boolean pagata;

	@ManyToOne()
	@JoinColumn(name = "player_id")
	@JsonBackReference
	private Player player;

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
	 * Gets the descrizione.
	 *
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * Sets the descrizione.
	 *
	 * @param descrizione
	 *            the new descrizione
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	/**
	 * Gets the valore.
	 *
	 * @return the valore
	 */
	public double getValore() {
		return valore;
	}

	/**
	 * Sets the valore.
	 *
	 * @param valore
	 *            the new valore
	 */
	public void setValore(double valore) {
		this.valore = valore;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * Checks if is pagata.
	 *
	 * @return true, if is pagata
	 */
	public boolean isPagata() {
		return pagata;
	}

	/**
	 * Sets the pagata.
	 *
	 * @param pagata
	 *            the new pagata
	 */
	public void setPagata(boolean pagata) {
		this.pagata = pagata;
	}

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player.
	 *
	 * @param player
	 *            the new player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

}
