package com.upp.apteka.bo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class PurchaseMedicineID implements Serializable{
	
	private static final long serialVersionUID = -3055444314495572720L;
	
	private Purchase purchase;
	private Medicine medicine;
	
	@ManyToOne
	public Purchase getPurchase() {
		return purchase;
	}
	
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
	@ManyToOne
	public Medicine getMedicine() {
		return medicine;
	}
	
	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}
}
