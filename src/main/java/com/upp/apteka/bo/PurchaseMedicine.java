package com.upp.apteka.bo;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "purch_medicine")
@AssociationOverrides({
		@AssociationOverride(name = "purchMedicine.medicine",
			joinColumns = @JoinColumn(name = "id_medicine")),
		@AssociationOverride(name = "purchMedicine.purchase",
			joinColumns = @JoinColumn(name = "id_purchase")) })
public class PurchaseMedicine {
	
	private PurchaseMedicineID purchMedicine = new PurchaseMedicineID();
	
	@Column(name = "pack_quantity", nullable = false)
	private int packQuantity;
	
	@EmbeddedId
	public PurchaseMedicineID getPurchMedicine(){
		return purchMedicine;
	}
	
	public void setPurchMedicine(PurchaseMedicineID purchMedicine){
		this.purchMedicine = purchMedicine;
	}

	@Transient
	public Medicine getMedicine() {
		return getPurchMedicine().getMedicine();
	}

	public void setMedicine(Medicine medicine) {
		getPurchMedicine().setMedicine(medicine);
	}

	@Transient
	public Purchase getPurchase() {
		return getPurchMedicine().getPurchase();
	}

	public void setPurchase(Purchase purchase) {
		getPurchMedicine().setPurchase(purchase);
	}

	public int getPackQuantity() {
		return packQuantity;
	}

	public void setPackQuantity(int packQuantity) {
		this.packQuantity = packQuantity;
	}
}
