package com.teamManager.dto;

/**
 * The Class ChartDataDTOExt.
 */
public class ChartDataDTOExt extends CharDataDTO {

	/**
	 * Instantiates a new chart data DTO ext.
	 *
	 * @param day
	 *            the day
	 * @param month
	 *            the month
	 * @param year
	 *            the year
	 * @param value
	 *            the value
	 */
	public ChartDataDTOExt(String day, String month, String year, Double value) {
		super(month, year, value);
		this.day = day;
	}

	private String day;

	/**
	 * Gets the day.
	 *
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * Sets the day.
	 *
	 * @param day
	 *            the new day
	 */
	public void setDay(String day) {
		this.day = day;
	}

}
