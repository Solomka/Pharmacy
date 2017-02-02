package com.upp.apteka.bo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class PrescriptionMedicineID implements Serializable{
	
	private static final long serialVersionUID = -3055444314495572720L;
	
	private Prescription prescription;
	private Medicine medicine;
	
	@ManyToOne
	public Prescription getPrescription() {
		return prescription;
	}
	
	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
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
		result = prime * result + ((prescription == null) ? 0 : prescription.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrescriptionMedicineID other = (PrescriptionMedicineID) obj;
		if (medicine == null) {
			if (other.medicine != null)
				return false;
		} else if (!medicine.equals(other.medicine))
			return false;
		if (prescription == null) {
			if (other.prescription != null)
				return false;
		} else if (!prescription.equals(other.prescription))
			return false;
		return true;
	}

}
