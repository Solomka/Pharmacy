package com.upp.apteka.bo;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "delivery_medicine")
@AssociationOverrides({
		@AssociationOverride(name = "deliveryMedicineID.delivery", joinColumns = @JoinColumn(name = "id_delivery") ),
		@AssociationOverride(name = "deliveryMedicineID.medicine", joinColumns = @JoinColumn(name = "id_medicine") ) })
public class DeliveryMedicine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2230382760027484141L;

	private DeliveryMedicineID deliveryMedicineID = new DeliveryMedicineID();

	private int boxQuantity;

	public DeliveryMedicine() {

	}

	public DeliveryMedicine(DeliveryMedicineID deliveryMedicineID) {

		this.deliveryMedicineID = deliveryMedicineID;
	}

	public DeliveryMedicine(DeliveryMedicineID deliveryMedicineID, int boxQuantity) {

		this.deliveryMedicineID = deliveryMedicineID;
		this.boxQuantity = boxQuantity;
	}

	public DeliveryMedicine(int boxQuantity) {

		this.boxQuantity = boxQuantity;
	}

	@EmbeddedId
	private DeliveryMedicineID getDeliveryMedicineID() {
		return deliveryMedicineID;
	}

	public void setDeliveryMedicineID(DeliveryMedicineID deliveryMedicineID) {
		this.deliveryMedicineID = deliveryMedicineID;
	}

	@Transient
	public Delivery getDelivery() {
		return getDeliveryMedicineID().getDelivery();
	}

	public void setDelivery(Delivery delivery) {
		getDeliveryMedicineID().setDelivery(delivery);
	}

	@Transient
	public Medicine getMedicine() {
		return getDeliveryMedicineID().getMedicine();
	}

	public void setMedicine(Medicine medicine) {
		getDeliveryMedicineID().setMedicine(medicine);

	}

	@Column(name = "box_quantity", nullable = false)
	public int getBoxQuantity() {
		return boxQuantity;
	}

	public void setBoxQuantity(int boxQuantity) {
		this.boxQuantity = boxQuantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getDeliveryMedicineID() == null) ? 0 : getDeliveryMedicineID().hashCode());
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
		if (!(obj instanceof DeliveryMedicine)) {
			return false;
		}
		DeliveryMedicine other = (DeliveryMedicine) obj;
		if (getDeliveryMedicineID() == null) {
			if (other.getDeliveryMedicineID() != null) {
				return false;
			}
		} else if (!getDeliveryMedicineID().equals(other.getDeliveryMedicineID())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DeliveryMedicine [deliveryMedicineID=[ delID: " + getDeliveryMedicineID().getDelivery().getId()
				+ ", medID: " + getDeliveryMedicineID().getMedicine().getId() + "] " + ", boxQuantity=" + boxQuantity
				+ "]";
	}

}
