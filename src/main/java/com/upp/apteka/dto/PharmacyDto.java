package com.upp.apteka.dto;

import java.awt.Container;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.utils.FrameContext;
import com.upp.apteka.validator.ValidationError;

@Component
@Scope("prototype")
public class PharmacyDto {

	@Autowired
	private FrameContext frameContext;

	private Long id;
	private String name;
	private String address;
	private double extra;

	private final static Logger logger = Logger.getLogger(PharmacyDto.class.getName());

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		long temp;
		temp = Double.doubleToLongBits(extra);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof PharmacyDto)) {
			return false;
		}
		PharmacyDto other = (PharmacyDto) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (Double.doubleToLongBits(extra) != Double.doubleToLongBits(other.extra)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PharmacyDto [name=" + name + ", address=" + address + ", extra=" + extra + "]";
	}

	public void readFromContext(Container container) {

		try {
			JTextField name = (JTextField) frameContext.findComponentByName(container, "form:name");
			JTextField address = (JTextField) frameContext.findComponentByName(container, "form:address");
			JTextField extra = (JTextField) frameContext.findComponentByName(container, "form:extra");

			this.name = name.getText();
			this.address = address.getText();
			this.extra = Double.parseDouble(extra.getText());
		} catch (Exception e) {
			logger.error("Невідповідність типу поля!");
		}
	}

	public void showErrors(List<ValidationError> errors, Container container) {

		String[] errorFields = { "error:name", "error:address", "error:extra" };

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
