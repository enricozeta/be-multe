package com.teamManager.dto;

import java.util.ArrayList;
import java.util.List;

import com.teamManager.model.Multa;

/**
 * The Class PlayerHome.
 */
public class PlayerHome {

	private Long id;

	private String name;

	private String surname;

	private Double total;

	private List<Multa> multe = new ArrayList<>();

	/**
	 * Instantiates a new player home.
	 *
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param surname
	 *            the surname
	 * @param total
	 *            the total
	 */
	public PlayerHome(Long id, String name, String surname, double total, List<Multa> multe) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.total = total;
		this.multe = multe;
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
	 * Gets the surname.
	 *
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets the surname.
	 *
	 * @param surname
	 *            the new surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total
	 *            the new total
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

	/**
	 * Gets the multe.
	 *
	 * @return the multe
	 */
	public List<Multa> getMulte() {
		return multe;
	}

	/**
	 * Sets the multe.
	 *
	 * @param multe
	 *            the new multe
	 */
	public void setMulte(List<Multa> multe) {
		this.multe = multe;
	}

}
