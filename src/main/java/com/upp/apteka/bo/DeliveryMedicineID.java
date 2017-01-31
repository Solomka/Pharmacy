package com.upp.apteka.bo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * class that represents the Composite Key for DeliveryMedicine
 *  
 * @author Solomka
 *
 */

@Embeddable
public class DeliveryMedicineID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6455737360091360251L;
	
	private Delivery delivery;
	private Medicine medicine;

	public DeliveryMedicineID() {
	}

	@ManyToOne
	public Delivery getDelivery() {
		return this.delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	@ManyToOne
	public Medicine getMedicine() {
		return this.medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((delivery == null) ? 0 : delivery.hashCode());
		result = prime * result + ((medicine == null) ? 0 : medicine.hashCode());
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
		if (!(obj instanceof DeliveryMedicineID)) {
			return false;
		}
		DeliveryMedicineID other = (DeliveryMedicineID) obj;
		if (delivery == null) {
			if (other.delivery != null) {
				return false;
			}
		} else if (!delivery.equals(other.delivery)) {
			return false;
		}
		if (medicine == null) {
			if (other.medicine != null) {
				return false;
			}
		} else if (!medicine.equals(other.medicine)) {
			return false;
		}
		return true;
	}		

}
