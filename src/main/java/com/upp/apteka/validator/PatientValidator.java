package com.upp.apteka.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.mysql.jdbc.StringUtils;
import com.upp.apteka.dto.PatientDto;

@Component
@Scope("prototype")
public class PatientValidator {

	public List<ValidationError> validate(Object target) {

		List<ValidationError> errors = new ArrayList<ValidationError>();
		PatientDto patientDto = (PatientDto) target;

		if (StringUtils.isEmptyOrWhitespaceOnly(patientDto.getSurname()))
			errors.add(new ValidationError("error:surname", "Потрібно вказати прізвище."));
		else if (patientDto.getSurname().length() > 50)
			errors.add(new ValidationError("error:surname", "Занадто довге прізвище."));

		if (StringUtils.isEmptyOrWhitespaceOnly(patientDto.getName()))
			errors.add(new ValidationError("error:name", "Потрібно вказати ім'я."));
		else if (patientDto.getName().length() > 50)
			errors.add(new ValidationError("error:name", "Занадто довге ім'я."));

		Pattern pattern = Pattern.compile("^[+]38[(][0-9]{3}[)]-[0-9]{3}-[0-9]{2}-[0-9]{2}$");
		Matcher matcher = pattern.matcher(patientDto.getPhone());

		if (!matcher.matches()) {
			errors.add(new ValidationError("error:phone", "Потрібно вказати номер у форматі +38(ХХХ)-ХХХ-ХХ-ХХ"));
		}

		return errors;
	}
}
