package com.upp.apteka.dto;

import com.upp.apteka.bo.Medicine;

public class MedicineShowDto {
	private Long id;
	private String name;
	private String producer;
	
	public MedicineShowDto(Medicine medicine){
		this.id = medicine.getId();
		this.name = medicine.getName();
		this.producer = medicine.getProducer();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}
	
	public String toString(){
		return name + " " + producer;
	}
}
