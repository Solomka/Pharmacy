package com.upp.apteka.dto;

public class ChartMedicineDto<T> {

	private T id;
	private String name;

	public ChartMedicineDto() {

	}

	public ChartMedicineDto(T id, String name) {

		this.id = id;
		this.name = name;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ChartMedicineDto [id=" + id + ", name=" + name + "]";
	}
	
	

}
