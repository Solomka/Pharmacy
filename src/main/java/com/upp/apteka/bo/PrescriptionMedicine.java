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
@Table(name = "prescr_medicine")
@AssociationOverrides({
		@AssociationOverride(name = "pk.prescription",
			joinColumns = @JoinColumn(name = "id_prescription")),
		@AssociationOverride(name = "pk.medicine",
			joinColumns = @JoinColumn(name = "id_medicine")) })
public class PrescriptionMedicine {
	
	private PrescriptionMedicineID pk = new PrescriptionMedicineID();
	
	@Column(name = "pack_quantity", nullable = false)
	private int packQuantity;
	
	@Column(name = "pack_bought", nullable = false)
	private int packBought;
	
	@EmbeddedId
	public PrescriptionMedicineID getPk(){
		return pk;
	}
	
	public void setPk(PrescriptionMedicineID pk){
		this.pk = pk;
	}

	@Transient
	public Medicine getMedicine() {
		return getPk().getMedicine();
	}

	public void setMedicine(Medicine medicine) {
		getPk().setMedicine(medicine);
	}

	@Transient
	public Prescription getPrescription() {
		return getPk().getPrescription();
	}

	public void setPrescription(Prescription prescription) {
		getPk().setPrescription(prescription);
	}

	public int getPackQuantity() {
		return packQuantity;
	}

	public void setPackQuantity(int packQuantity) {
		this.packQuantity = packQuantity;
	}

	public int getPackBought() {
		return packBought;
	}

	public void setPackBought(int packBought) {
		this.packBought = packBought;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
		PrescriptionMedicine other = (PrescriptionMedicine) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
}
