package com.upp.apteka.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
