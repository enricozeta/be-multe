package com.teamManager.dto;

public class CharDataDTO {

	private String month;
	private String year;
	private Double value;

	public CharDataDTO(String month, String year, Double value) {
		super();
		this.month = month;
		this.year = year;
		this.value = value;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getSort() {
		return Integer.parseInt(this.year.toString() + this.month.toString());
	}

}
