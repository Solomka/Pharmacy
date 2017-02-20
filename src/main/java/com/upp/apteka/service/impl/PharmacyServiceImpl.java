package com.upp.apteka.service.impl;

import java.awt.Container;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Constants;
import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.dto.PharmacyDto;
import com.upp.apteka.repository.PharmacyRepository;
import com.upp.apteka.service.PharmacyService;
import com.upp.apteka.validator.PharmacyValidator;
import com.upp.apteka.validator.ValidationError;

import javassist.NotFoundException;

@Service("pharmacyService")
@Transactional
public class PharmacyServiceImpl implements PharmacyService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	PharmacyRepository pharmacyRepository;

	// get all pharmacies ordered by name
	public List<Pharmacy> getAllPharmacies(int offset) {

		return pharmacyRepository.getAll(offset, Constants.LIMIT);
	}

	public Pharmacy getPharmacy(Long idPharmacy) {

		return pharmacyRepository.read(idPharmacy);
	}

	public Long addPharmacy(Pharmacy pharmacy) {

		return pharmacyRepository.create(pharmacy);
	}

	public void updatePharmacy(Pharmacy pharmacy) {

		pharmacyRepository.update(pharmacy);
	}

	public boolean deletePharmacy(Long idPharmacy) {

		return pharmacyRepository.delete(idPharmacy);
	}

	public List<Pharmacy> getPharmacyByName(String name) {

		return pharmacyRepository.findPharmacyByName(name);
	}

	public boolean containsAddress(String address) {
		return pharmacyRepository.containsAddress(address);
	}

	public List<ValidationError> processAdding(Container container) throws NotFoundException {
		PharmacyDto pharmacyDto = applicationContext.getBean(PharmacyDto.class);
		pharmacyDto.readFromContext(container);

		PharmacyValidator pharmacyValidator = applicationContext.getBean(PharmacyValidator.class);

		List<ValidationError> errors = pharmacyValidator.validate(pharmacyDto);
		pharmacyDto.showErrors(errors, container);

		if (errors.isEmpty()) {
			System.err.println("No errors");
			addPharmacy(new Pharmacy(pharmacyDto));
		}

		return errors;
	}

	public List<ValidationError> processEditing(Container container, Long id) throws NotFoundException {
		PharmacyDto pharmacyDto = applicationContext.getBean(PharmacyDto.class);
		pharmacyDto.readFromContext(container);

		PharmacyValidator pharmacyValidator = applicationContext.getBean(PharmacyValidator.class);

		List<ValidationError> errors = pharmacyValidator.validate(pharmacyDto);
		pharmacyDto.showErrors(errors, container);

		Pharmacy pharmacy = new Pharmacy(pharmacyDto);
		pharmacy.setId(id);

		if (errors.isEmpty())
			updatePharmacy(pharmacy);

		return errors;
	}

}
