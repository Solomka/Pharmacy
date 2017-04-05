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
public class DoctorDto {

	@Autowired
	private FrameContext frameContext;

	private String name;
	private String surname;
	private String occupation;
	private int standing;

	private final static Logger logger = Logger.getLogger(DoctorDto.class.getName());

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((frameContext == null) ? 0 : frameContext.hashCode());
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
		DoctorDto other = (DoctorDto) obj;
		if (frameContext == null) {
			if (other.frameContext != null)
				return false;
		} else if (!frameContext.equals(other.frameContext))
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
		return "DoctorDto [frameContext=" + frameContext + ", name=" + name + ", surname=" + surname + ", occupation="
				+ occupation + ", standing=" + standing + "]";
	}

	//Container == JFrame
	//A generic Abstract Window Toolkit(AWT) container object is a component that can contain other AWT components.
	//construct this.DoctorDTO from Form inputs
	public void readFromContext(Container container) {

		try {
			JTextField name = (JTextField) frameContext.findComponentByName(container, "form:name");
			JTextField surname = (JTextField) frameContext.findComponentByName(container, "form:surname");
			JTextField standing = (JTextField) frameContext.findComponentByName(container, "form:standing");
			JTextField occupation = (JTextField) frameContext.findComponentByName(container, "form:occupation");

			this.name = name.getText();
			this.surname = surname.getText();

			try {
				String text = standing.getText().replace(" ", "");
				this.standing = Integer.parseInt(text.replaceAll(",", ""));
				
			} catch (Exception e) {
				this.standing = 0;
			}
			this.occupation = occupation.getText();

		} catch (Exception e) {
			logger.error("Невідповідність типу поля!");
		}
	}

	public void showErrors(List<ValidationError> errors, Container container) {

		String[] errorFields = { "error:name", "error:surname", "error:standing", "error:occupation" };

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
