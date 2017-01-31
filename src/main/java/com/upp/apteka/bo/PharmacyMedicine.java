package com.upp.apteka.bo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "pharmacy_medicine")
@AssociationOverrides({
		@AssociationOverride(name = "pharmacyMedicineID.pharmacy", joinColumns = @JoinColumn(name = "id_pharmacy") ),
		@AssociationOverride(name = "pharmacyMedicineID.medicine", joinColumns = @JoinColumn(name = "id_medicine") ) })
public class PharmacyMedicine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -680116704935088599L;

	private PharmacyMedicineID pharmacyMedicineID = new PharmacyMedicineID();

	private BigDecimal packPrice;
	private int packQuantity;

	public PharmacyMedicine() {

	}

	public PharmacyMedicine(PharmacyMedicineID pharmacyMedicineID) {

		this.pharmacyMedicineID = pharmacyMedicineID;
	}

	public PharmacyMedicine(PharmacyMedicineID pharmacyMedicineID, BigDecimal packPrice, int packQuantity) {

		this.pharmacyMedicineID = pharmacyMedicineID;
		this.packPrice = packPrice;
		this.packQuantity = packQuantity;
	}

	public PharmacyMedicine(BigDecimal packPrice, int packQuantity) {

		this.packPrice = packPrice;
		this.packQuantity = packQuantity;
	}

	@EmbeddedId
	private PharmacyMedicineID getPharmacyMedicineID() {
		return pharmacyMedicineID;
	}

	private void setPharmacyMedicineID(PharmacyMedicineID pharmacyMedicineID) {
		this.pharmacyMedicineID = pharmacyMedicineID;
	}

	@Transient
	public Pharmacy getPharmacy() {
		return getPharmacyMedicineID().getPharmacy();
	}

	public void setPharmacy(Pharmacy pharmacy) {
		getPharmacyMedicineID().setPharmacy(pharmacy);
	}

	@Transient
	public Medicine getMedicine() {
		return getPharmacyMedicineID().getMedicine();
	}

	public void setMedicine(Medicine medicine) {
		getPharmacyMedicineID().setMedicine(medicine);
	}

	@Column(name = "pack_price", nullable = false)
	public BigDecimal getPackPrice() {
		return packPrice;
	}

	public void setPackPrice(BigDecimal packPrice) {
		this.packPrice = packPrice;
	}

	@Column(name = "pack_quantity", nullable = false)
	public int getPackQuantity() {
		return packQuantity;
	}

	public void setPackQuantity(int packQuantity) {
		this.packQuantity = packQuantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getPharmacyMedicineID() == null) ? 0 : getPharmacyMedicineID().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PharmacyMedicine)) {
			return false;
		}
		PharmacyMedicine other = (PharmacyMedicine) obj;
		if (getPharmacyMedicineID() == null) {
			if (other.getPharmacyMedicineID() != null) {
				return false;
			}
		} else if (!getPharmacyMedicineID().equals(other.getPharmacyMedicineID())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PharmacyMedicine [pharmacyMedicineID= [pharmId: " + getPharmacyMedicineID().getPharmacy().getId()
				+ ", medId: " + getPharmacyMedicineID().getMedicine().getId() + " ], " + ", packPrice=" + packPrice
				+ ", packQuantity=" + packQuantity + "]";
	}

}
