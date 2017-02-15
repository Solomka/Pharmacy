package com.upp.apteka.bo;

import java.io.Serializable;
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

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "prescription")
public class Prescription implements Serializable{
	
	private static final long serialVersionUID = 7233245492808399625L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_doctor", nullable = false)
	private Doctor doctor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_patient", nullable = false)
	private Patient patient;
	
	@Column(name = "date", updatable = false, nullable = false)
	private Date date;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.prescription")
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	private List<PrescriptionMedicine> prescriptionMedicines;
	
	public Prescription(){
		
	}

	public Prescription(Long id, Doctor doctor, Patient patient, Date date) {
		super();
		this.id = id;
		this.doctor = doctor;
		this.patient = patient;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<PrescriptionMedicine> getPrescriptionMedicines() {
		return prescriptionMedicines;
	}

	public void setPrescriptionMedicines(List<PrescriptionMedicine> prescriptionMedicines) {
		this.prescriptionMedicines = prescriptionMedicines;
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
		Prescription other = (Prescription) obj;
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

	@Override
	public String toString() {
		return "Prescription [id=" + id + ", date=" + date + "]";
	}
}
