package com.upp.apteka.bo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * class that represents the Composite Primary Key forPharmacyMedicine
 * 
 * @author Solomka
 *
 */

@Embeddable
public class PharmacyMedicineID implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7051257863288751597L;
	private Pharmacy pharmacy;
	private Medicine medicine;
	
	public PharmacyMedicineID(){
		
	}

	@ManyToOne
	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	@ManyToOne
	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((medicine == null) ? 0 : medicine.hashCode());
		result = prime * result + ((pharmacy == null) ? 0 : pharmacy.hashCode());
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
		if (!(obj instanceof PharmacyMedicineID)) {
			return false;
		}
		PharmacyMedicineID other = (PharmacyMedicineID) obj;
		if (medicine == null) {
			if (other.medicine != null) {
				return false;
			}
		} else if (!medicine.equals(other.medicine)) {
			return false;
		}
		if (pharmacy == null) {
			if (other.pharmacy != null) {
				return false;
			}
		} else if (!pharmacy.equals(other.pharmacy)) {
			return false;
		}
		return true;
	}
	
	
	

}
