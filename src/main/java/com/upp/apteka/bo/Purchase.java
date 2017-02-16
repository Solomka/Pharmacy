package com.upp.apteka.bo;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "purchase")
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "date", updatable = false, nullable = false)
	private Date date;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_patient", nullable = false, updatable = false)
	private Patient patient;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_prescr", nullable = false, updatable = false)
	private Prescription prescription;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_pharmacy", nullable = false, updatable = false)
	private Pharmacy pharmacy;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchMedicine.purchase")
	private List<PurchaseMedicine> purchaseMedicines;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	public List<PurchaseMedicine> getPurchaseMedicines() {
		return purchaseMedicines;
	}

	public void setPurchaseMedicines(List<PurchaseMedicine> purchaseMedicines) {
		this.purchaseMedicines = purchaseMedicines;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Purchase other = (Purchase) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
