package com.upp.apteka.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.StringUtils;
import com.upp.apteka.dto.PharmacyDto;
import com.upp.apteka.service.PharmacyService;

@Component
@Scope("prototype")
public class PharmacyValidator implements Validator {

	@Autowired
	PharmacyService pharmacyService;

	private static final int minExtra = 1;
	private static final int maxExtra = 100;

	public List<ValidationError> validate(Object target) {

		List<ValidationError> errors = new ArrayList<ValidationError>();
		PharmacyDto pharmacyDto = (PharmacyDto) target;

		if (StringUtils.isEmptyOrWhitespaceOnly(pharmacyDto.getName())) {
			errors.add(new ValidationError("error:name", "Потрібно вказати назву."));
		} else if (pharmacyDto.getName().length() > 50) {
			errors.add(new ValidationError("error:name", "Занадто довга назва."));
		}

		if (StringUtils.isEmptyOrWhitespaceOnly(pharmacyDto.getAddress())) {
			errors.add(new ValidationError("error:address", "Потрбіно вказати адресу."));
		} else if (pharmacyDto.getAddress().length() > 255) {
			errors.add(new ValidationError("error:address", "Занадто довга адреса"));
		} else if (pharmacyDto.getId() == null && pharmacyService.containsAddress(pharmacyDto.getAddress())) {
			errors.add(new ValidationError("error:address", "Аптека з такою адресою уже існує"));
		}

		if (pharmacyDto.getExtra() < minExtra || pharmacyDto.getExtra() > maxExtra) {
			errors.add(new ValidationError("error:extra",
					"Націнка повинна бути від" + minExtra + "% до " + maxExtra + "%."));
		}

		return errors;
	}

}
