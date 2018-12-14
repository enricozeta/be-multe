package com.teamManager.dto;

import java.util.Date;

/**
 * The Class MultaDTO.
 */
public class MultaDTO {

	private Long id;

	private String descrizione;

	private double valore;

	private Date data;

	private boolean pagata;

	private Long playerId;

	private Long multeTypeId;

	public MultaDTO() {

	}

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
	 * Gets the playerId.
	 *
	 * @return the playerId
	 */
	public Long getPlayerId() {
		return playerId;
	}

	/**
	 * Sets the player.
	 *
	 * @param player
	 *            the new player
	 */
	public void setPlayer(Long playerId) {
		this.playerId = playerId;
	}

	/**
	 * Gets the multeTypeId.
	 *
	 * @return the multeTypeId
	 */
	public Long getmulteTypeId() {
		return multeTypeId;
	}

	/**
	 * Sets the multeTypeId.
	 *
	 * @param multeType
	 *            the new multeTypeId
	 */
	public void setMulteTypeId(Long multeTypeId) {
		this.multeTypeId = multeTypeId;
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
	 * @param playerId
	 *            the playerId
	 * @param multeType
	 *            the multe type
	 */
	public MultaDTO(Long id, String descrizione, double valore, Date data, boolean pagata, Long playerId,
			Long multeTypeId) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.valore = valore;
		this.data = data;
		this.pagata = pagata;
		this.playerId = playerId;
		this.multeTypeId = multeTypeId;
	}

}
