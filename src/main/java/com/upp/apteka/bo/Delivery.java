package com.upp.apteka.bo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "delivery")
public class Delivery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1161166295546331546L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "date", updatable = false, nullable = false)
	private Timestamp date;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_pharmacy", nullable = false, updatable = false)
	private Pharmacy pharmacy;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryMedicineID.delivery", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE })
	private List<DeliveryMedicine> deliveryMedicines = new ArrayList<DeliveryMedicine>();

	public Delivery() {

	}

	public Delivery(Long id) {

		this.id = id;
	}

	public Delivery(Long id, Timestamp date, Pharmacy pharmacy) {

		this.id = id;
		this.date = date;
		this.pharmacy = pharmacy;
	}

	public Delivery(Timestamp date, Pharmacy pharmacy) {

		this.date = date;
		this.pharmacy = pharmacy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public List<DeliveryMedicine> getDeliveryMedicines() {
		return deliveryMedicines;
	}

	protected void setDeliveryMedicines(List<DeliveryMedicine> deliveryMedicines) {
		this.deliveryMedicines = deliveryMedicines;
	}

	/**
	 * add Delivery to DeliveryMedicine and vice versa
	 * 
	 * @param orderItemPO
	 */
	public void addToDeliveryMedicine(DeliveryMedicine deliveryMedicine) {
		deliveryMedicine.setDelivery(this);
		
		this.deliveryMedicines.add(deliveryMedicine);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
		result = prime * result + ((getPharmacy() == null) ? 0 : getPharmacy().hashCode());
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
		if (!(obj instanceof Delivery)) {
			return false;
		}
		Delivery other = (Delivery) obj;
		if (getDate() == null) {
			if (other.getDate() != null) {
				return false;
			}
		} else if (!getDate().equals(other.getDate())) {
			return false;
		}
		if (getPharmacy() == null) {
			if (other.getPharmacy() != null) {
				return false;
			}
		} else if (!pharmacy.equals(other.pharmacy)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Delivery [id=" + id + ", date=" + date + ", pharmacy=" + pharmacy.toString() + "]";
	}

}
