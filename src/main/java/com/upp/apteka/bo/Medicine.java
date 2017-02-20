package com.upp.apteka.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import org.hibernate.annotations.Cascade;

import com.upp.apteka.dto.MedicineDto;

@Entity
@Table(name = "medicine")
public class Medicine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7634536212916916543L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "producer", nullable = false, length = 255)
	private String producer;

	@Column(name = "box_price", nullable = false)
	private BigDecimal boxPrice;

	@Column(name = "quantity_per_box", nullable = false)
	private int quantityPerBox;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pharmacyMedicineID.medicine", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })

	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	private List<PharmacyMedicine> pharmacyMedicines;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryMedicineID.medicine", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	private List<DeliveryMedicine> deliveryMedicines = new ArrayList<DeliveryMedicine>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchMedicine.medicine")
	private Set<PurchaseMedicine> purchaseMedicines;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.medicine")
	private Set<PrescriptionMedicine> prescriptionMedicines;

	public Medicine() {

	}

	public Medicine(Long id) {
		this.id = Objects.requireNonNull(id, "id can not be null");
	}

	public Medicine(Long id, String name, String producer, BigDecimal boxPrice, int quantityPerBox) {
		this.id = Objects.requireNonNull(id, "id can not be null");
		this.name = Objects.requireNonNull(name, "name can not be null");
		this.producer = Objects.requireNonNull(producer, "producer can not be null");
		this.boxPrice = Objects.requireNonNull(boxPrice, "boxPrice can not be null");
		this.quantityPerBox = Objects.requireNonNull(quantityPerBox, "quantity per box can not be null");
	}

	public Medicine(String name, String producer, BigDecimal boxPrice, int quantityPerBox) {
		this.name = Objects.requireNonNull(name, "name can not be null");
		this.producer = Objects.requireNonNull(producer, "producer can not be null");
		this.boxPrice = Objects.requireNonNull(boxPrice, "boxPrice can not be null");
		this.quantityPerBox = Objects.requireNonNull(quantityPerBox, "quantity per box can not be null");
	}

	public Medicine(MedicineDto medicineDto) {
		this.name = medicineDto.getName();
		this.producer = medicineDto.getProducer();
		this.boxPrice = medicineDto.getBoxPrice();
		this.quantityPerBox = medicineDto.getQuantityPerBox();

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

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public BigDecimal getBoxPrice() {
		return boxPrice;
	}

	public void setBoxPrice(BigDecimal boxPrice) {
		this.boxPrice = boxPrice;
	}

	public int getQuantityPerBox() {
		return quantityPerBox;
	}

	public void setQuantityPerBox(int quantityPerBox) {
		this.quantityPerBox = quantityPerBox;
	}

	public List<PharmacyMedicine> getPharmacyMedicines() {
		return pharmacyMedicines;
	}

	public void setPharmacyMedicines(List<PharmacyMedicine> pharmacyMedicines) {
		this.pharmacyMedicines = pharmacyMedicines;
	}

	public List<DeliveryMedicine> getDeliveryMedicines() {
		return deliveryMedicines;
	}

	public void setDeliveryMedicines(List<DeliveryMedicine> deliveryMedicines) {
		this.deliveryMedicines = deliveryMedicines;
	}

	public Set<PrescriptionMedicine> getPrescriptionMedicines() {
		return prescriptionMedicines;
	}

	public void setPrescriptionMedicines(Set<PrescriptionMedicine> prescriptionMedicines) {
		this.prescriptionMedicines = prescriptionMedicines;
	}

	public Set<PurchaseMedicine> getPurchaseMedicines() {
		return purchaseMedicines;
	}

	public void setPurchaseMedicines(Set<PurchaseMedicine> purchaseMedicines) {
		this.purchaseMedicines = purchaseMedicines;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getBoxPrice() == null) ? 0 : getBoxPrice().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result + ((getProducer() == null) ? 0 : getProducer().hashCode());
		result = prime * result + getQuantityPerBox();
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
		if (!(obj instanceof Medicine)) {
			return false;
		}
		Medicine other = (Medicine) obj;
		if (getBoxPrice() == null) {
			if (other.getBoxPrice() != null) {
				return false;
			}
		} else if (!getBoxPrice().equals(other.getBoxPrice())) {
			return false;
		}
		if (getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!getName().equals(other.getName())) {
			return false;
		}
		if (getProducer() == null) {
			if (other.getProducer() != null) {
				return false;
			}
		} else if (!getProducer().equals(other.getProducer())) {
			return false;
		}
		if (getQuantityPerBox() != other.getQuantityPerBox()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Medicine [id=" + id + ", name=" + name + ", producer=" + producer + ", boxPrice=" + boxPrice
				+ ", quantityPerBox=" + quantityPerBox + "]";
	}

}
