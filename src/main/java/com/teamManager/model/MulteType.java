package com.teamManager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The Class MulteType.
 */
@Entity
public class MulteType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descrizione;

	private double valore;

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

}
