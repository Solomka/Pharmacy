package com.upp.apteka.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.StringUtils;
import com.upp.apteka.dto.MedicineDto;
import com.upp.apteka.service.MedicineService;

@Component
@Scope("prototype")
public class MedicineValidator implements Validator {

	@Autowired
	MedicineService medicineService;

	private static final BigDecimal minBoxPrice = BigDecimal.ONE;
	private static final BigDecimal maxBoxPrice = new BigDecimal(100000);

	private static final int minQuantityPerBox = 1;
	private static final int maxQuantityPerBox = 100000;

	public List<ValidationError> validate(Object target) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		MedicineDto medicineDto = (MedicineDto) target;

		if (StringUtils.isEmptyOrWhitespaceOnly(medicineDto.getName())) {
			errors.add(new ValidationError("error:name", "Потрібно вказати назву."));

		} else if (medicineDto.getName().length() > 255) {
			errors.add(new ValidationError("error:name", "Занадто довга назва."));
		}

		if (StringUtils.isEmptyOrWhitespaceOnly(medicineDto.getProducer())) {
			errors.add(new ValidationError("error:producer", "Потрбіно вказати виробника."));
		} else if (medicineDto.getProducer().length() > 255) {
			errors.add(new ValidationError("error:producer", "Занадто довга назва виробника."));
		} else if (medicineDto.getId() == null
				&& medicineService.containsNameProducerMedicine(medicineDto.getName(), medicineDto.getProducer())) {
			errors.add(new ValidationError("error:producer", "Ліки з такою назвою і виробником уже існують."));
		}

		final BigDecimal boxPrice = medicineDto.getBoxPrice();

		if (boxPrice.compareTo(minBoxPrice) < 0 || boxPrice.compareTo(maxBoxPrice) > 0) {
			errors.add(new ValidationError("error:boxPrice", "Ціна за коробку має бути від " + minBoxPrice.toString()
					+ " до " + maxBoxPrice.toString() + " грн."));

		}

		final int quantityPerBox = medicineDto.getQuantityPerBox();

		if (quantityPerBox < minQuantityPerBox || quantityPerBox > maxQuantityPerBox) {
			errors.add(new ValidationError("error:quantityPerBox", "К-сть упаковок у коробці має бути від "
					+ minQuantityPerBox + " до " + maxQuantityPerBox + " одн."));
		}

		return errors;
	}

}
