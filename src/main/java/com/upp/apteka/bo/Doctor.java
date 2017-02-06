package com.upp.apteka.bo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.upp.apteka.dto.DoctorDto;

@Entity
@Table(name = "doctor")
public class Doctor implements Serializable{

	private static final long serialVersionUID = 7464341851384183071L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 50)
	private String name;
	
	@Column(name = "surname", nullable = false, length = 50)
	private String surname;
	
	@Column(name = "occupation", nullable = false, length = 50)
	private String occupation;
	
	@Column(name = "standing", nullable = false)
	private int standing;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
	private Set<Prescription> prescriptions;
	
	public Doctor(DoctorDto doctorDto){
		this.name = doctorDto.getName();
		this.occupation = doctorDto.getOccupation();
		this.surname = doctorDto.getSurname();
		this.standing = doctorDto.getStanding();
	}
	
	public Doctor(){
		
	}
	
	public Doctor(Long id, String name, String surname, String occupation, int standing) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.occupation = occupation;
		this.standing = standing;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getStanding() {
		return standing;
	}

	public void setStanding(int standing) {
		this.standing = standing;
	}

	public Set<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + standing;
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		Doctor other = (Doctor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (standing != other.standing)
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", name=" + name + ", surname=" + surname + ", occupation=" + occupation
				+ ", standing=" + standing + "]";
	}
}
