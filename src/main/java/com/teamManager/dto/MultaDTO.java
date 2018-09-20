package com.teamManager.dto;

import java.util.Date;

import com.teamManager.model.MulteType;
import com.teamManager.model.Player;

/**
 * The Class MultaDTO.
 */
public class MultaDTO {

	private Long id;

	private String descrizione;

	private double valore;

	private Date data;

	private boolean pagata;

	private Player player;

	private MulteType multeType;

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

	/**
	 * Gets the multe type.
	 *
	 * @return the multe type
	 */
	public MulteType getMulteType() {
		return multeType;
	}

	/**
	 * Sets the multe type.
	 *
	 * @param multeType
	 *            the new multe type
	 */
	public void setMulteType(MulteType multeType) {
		this.multeType = multeType;
	}

	/**
	 * Instantiates a new multa DTO.
	 *
	 * @param id
	 *            the id
	 * @param descrizione
	 *            the descrizione
	 * @param valore
	 *            the valore
	 * @param data
	 *            the data
	 * @param pagata
	 *            the pagata
	 * @param player
	 *            the player
	 * @param multeType
	 *            the multe type
	 */
	public MultaDTO(Long id, String descrizione, double valore, Date data, boolean pagata, Player player,
			MulteType multeType) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.valore = valore;
		this.data = data;
		this.pagata = pagata;
		this.player = player;
		this.multeType = multeType;
	}

	/**
	 * Instantiates a new multa DTO.
	 *
	 * @param id
	 *            the id
	 * @param descrizione
	 *            the descrizione
	 * @param valore
	 *            the valore
	 * @param data
	 *            the data
	 * @param pagata
	 *            the pagata
	 * @param player
	 *            the player
	 */
	public MultaDTO(Long id, String descrizione, double valore, Date data, boolean pagata, Player player) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.valore = valore;
		this.data = data;
		this.pagata = pagata;
		this.player = player;
	}

}
