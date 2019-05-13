package com.teamManager.dto;

public class CharDataDTO {

	private Integer month;
	private Integer year;
	private Double value;

	public CharDataDTO(Integer month, Integer year, Double value) {
		super();
		this.month = month;
		this.year = year;
		this.value = value;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
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
