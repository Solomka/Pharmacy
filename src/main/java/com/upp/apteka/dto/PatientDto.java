package com.upp.apteka.dto;

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
public class PatientDto {

	@Autowired
	private FrameContext frameContext;

	private String name;
	private String surname;
	private String phone;
	
	private final static Logger logger = Logger.getLogger(PatientDto.class.getName());

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
		PatientDto other = (PatientDto) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
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
		return "PatientDto [name=" + name + ", surname=" + surname + ", phone=" + phone + "]";
	}

	public void readFromContext() {

		try {
			JTextField name = (JTextField) frameContext.findComponentByName("form:name");
			JTextField surname = (JTextField) frameContext.findComponentByName("form:surname");
			JTextField phone = (JTextField) frameContext.findComponentByName("form:phone");

			this.name = name.getText();
			this.surname = surname.getText();
			this.phone = phone.getText();
		} catch (Exception e) {
			logger.error("Невідповідність типу поля!");
		}
	}

	public void showErrors(List<ValidationError> errors) {

		String[] errorFields = { "error:name", "error:surname", "error:phone" };

		for (String errorField : errorFields)
			try {
				JLabel label = (JLabel) frameContext.findComponentByName(errorField);
				label.setText("");
			} catch (Exception e) {
				logger.warn("Відсутнє поле для виводу помилки: " + errorField);
			}

		for (ValidationError error : errors) {
			try {
				JLabel label = (JLabel) frameContext.findComponentByName(error.getObjectName());
				label.setText(error.getDescription());
			} catch (Exception e) {
				logger.warn("Відсутнє поле для виводу помилки: " + error);
			}
		}
	}
}
