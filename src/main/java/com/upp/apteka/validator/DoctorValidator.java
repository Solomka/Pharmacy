package com.upp.apteka.validator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.mysql.jdbc.StringUtils;
import com.upp.apteka.dto.DoctorDto;

@Component
@Scope("prototype")
public class DoctorValidator implements Validator {

	public List<ValidationError> validate(Object target) {

		List<ValidationError> errors = new ArrayList<ValidationError>();
		DoctorDto doctorDto = (DoctorDto) target;

		if (StringUtils.isEmptyOrWhitespaceOnly(doctorDto.getSurname()))
			errors.add(new ValidationError("error:surname", "Потрібно вказати прізвище."));
		else if (doctorDto.getSurname().length() > 50)
			errors.add(new ValidationError("error:surname", "Занадто довге прізвище."));

		if (StringUtils.isEmptyOrWhitespaceOnly(doctorDto.getName()))
			errors.add(new ValidationError("error:name", "Потрібно вказати ім'я."));
		else if (doctorDto.getName().length() > 50)
			errors.add(new ValidationError("error:name", "Занадто довге ім'я."));

		if (StringUtils.isEmptyOrWhitespaceOnly(doctorDto.getOccupation()))
			errors.add(new ValidationError("error:occupation", "Потрібно вказати спеціальність."));
		else if (doctorDto.getOccupation().length() > 50)
			errors.add(new ValidationError("error:occupation", "Занадто довга спеціальність."));

		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);

		if (doctorDto.getStanding() < 1900 || doctorDto.getStanding() > year)
			errors.add(new ValidationError("error:standing", "Неправильний рік початку роботи."));

		return errors;
	}
}
