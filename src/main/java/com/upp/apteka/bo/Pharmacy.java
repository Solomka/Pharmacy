package com.upp.apteka.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pharmacy")
public class Pharmacy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4746274759493205856L;

	/**
	 * 
	 * using Long instead of long you can tell that a recently instantiated
	 * object is not yet persisted by checking for id==null
	 * 
	 * If you use long, then id will always have a non-null value (initially 0)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "address", nullable = false, length = 512)
	private String address;

	@Column(name = "extra", nullable = false)
	private double extra;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pharmacyMedicineID.pharmacy")
	private List<PharmacyMedicine> pharmacyMedicines;

	@OneToMany(mappedBy = "pharmacy", cascade = CascadeType.REFRESH)
	private List<Delivery> deliveries;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pharmacy")
	private Set<Purchase> purchases;

	public Pharmacy() {

	}

	public Pharmacy(Long id) {
		this.id = Objects.requireNonNull(id, "id can not be null");
	}

	public Pharmacy(Long id, String name, String address, double extra) {
		this.id = Objects.requireNonNull(id, "id can not be null");
		this.name = Objects.requireNonNull(name, "name can not be null");
		this.address = Objects.requireNonNull(address, "address can not be null");
		this.extra = Objects.requireNonNull(extra, "extra can not be null");

	}

	public Pharmacy(String name, String address, double extra) {
		this.name = Objects.requireNonNull(name, "name can not be null");
		this.address = Objects.requireNonNull(address, "address can not be null");
		this.extra = Objects.requireNonNull(extra, "extra can not be null");

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getExtra() {
		return extra;
	}

	public void setExtra(double extra) {
		this.extra = extra;
	}

	public List<PharmacyMedicine> getPharmacyMedicines() {
		return pharmacyMedicines;
	}

	public void setPharmacyMedicines(List<PharmacyMedicine> pharmacyMedicines) {
		this.pharmacyMedicines = pharmacyMedicines;
	}	
	
	public List<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}
	
	public Set<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}

	/**
	 * rewrite fields access to getters access for props because of Hibernate
	 * proxy
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
		long temp;
		temp = Double.doubleToLongBits(getExtra());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
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
		if (!(obj instanceof Pharmacy)) {
			return false;
		}
		Pharmacy other = (Pharmacy) obj;
		if (getAddress() == null) {
			if (other.getAddress() != null) {
				return false;
			}
		} else if (!getAddress().equals(other.getAddress())) {
			return false;
		}
		if (Double.doubleToLongBits(getExtra()) != Double.doubleToLongBits(other.getExtra())) {
			return false;
		}
		if (getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!getName().equals(other.getName())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Pharmacy [id=" + id + ", name=" + name + ", address=" + address + ", extra=" + extra + "]";
	}

}
