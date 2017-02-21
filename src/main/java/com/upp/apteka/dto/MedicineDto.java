package com.upp.apteka.dto;

import java.awt.Container;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.utils.FrameContext;
import com.upp.apteka.utils.validation.CValidationUtils;
import com.upp.apteka.validator.ValidationError;

@Component
@Scope("prototype")
public class MedicineDto {

	@Autowired
	private FrameContext frameContext;

	private Long id;
	private String name;
	private String producer;
	private BigDecimal boxPrice;
	private int quantityPerBox;

	private final static Logger logger = Logger.getLogger(MedicineDto.class.getName());
	
	

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
		result = prime * result + ((boxPrice == null) ? 0 : boxPrice.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((producer == null) ? 0 : producer.hashCode());
		result = prime * result + quantityPerBox;
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
		if (!(obj instanceof MedicineDto)) {
			return false;
		}
		MedicineDto other = (MedicineDto) obj;
		if (boxPrice == null) {
			if (other.boxPrice != null) {
				return false;
			}
		} else if (!boxPrice.equals(other.boxPrice)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (producer == null) {
			if (other.producer != null) {
				return false;
			}
		} else if (!producer.equals(other.producer)) {
			return false;
		}
		if (quantityPerBox != other.quantityPerBox) {
			return false;
		}
		return true;
	}

	
	@Override
	public String toString() {
		return "MedicineDto [id=" + id + ", name=" + name + ", producer=" + producer + ", boxPrice=" + boxPrice
				+ ", quantityPerBox=" + quantityPerBox + "]";
	}

	public void readFromContext(Container container) {

		try {
			JTextField name = (JTextField) frameContext.findComponentByName(container, "form:name");
			JTextField producer = (JTextField) frameContext.findComponentByName(container, "form:producer");
			JTextField boxPrice = (JTextField) frameContext.findComponentByName(container, "form:boxPrice");
			JTextField quantityPerBox = (JTextField) frameContext.findComponentByName(container, "form:quantityPerBox");

			this.name = name.getText();
			this.producer = producer.getText();

			// this.boxPrice = new BigDecimal(boxPrice.getText(),
			// MathContext.DECIMAL64);
			this.boxPrice = CValidationUtils.fromStringToBigDecimal(boxPrice.getText());
			this.quantityPerBox = Integer.parseInt(quantityPerBox.getText());
		} catch (Exception e) {
			logger.error("Невідповідність типу поля!");
		}
	}

	public void showErrors(List<ValidationError> errors, Container container) {

		String[] errorFields = { "error:name", "error:producer", "error:boxPrice", "error:quantityPerBox" };

		for (String errorField : errorFields)
			try {
				JLabel label = (JLabel) frameContext.findComponentByName(container, errorField);
				label.setText("");
			} catch (Exception e) {
				logger.warn("Відсутнє поле для виводу помилки: " + errorField);
			}

		for (ValidationError error : errors) {
			try {
				JLabel label = (JLabel) frameContext.findComponentByName(container, error.getObjectName());
				label.setText(error.getDescription());
			} catch (Exception e) {
				logger.warn("Відсутнє поле для виводу помилки: " + error);
			}
		}
	}

}
