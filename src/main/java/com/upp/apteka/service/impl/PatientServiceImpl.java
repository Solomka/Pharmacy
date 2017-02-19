package com.upp.apteka.service.impl;

import java.awt.Container;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.upp.apteka.bo.Patient;
import com.upp.apteka.dto.PatientDto;
import com.upp.apteka.repository.PatientRepository;
import com.upp.apteka.service.PatientService;
import com.upp.apteka.validator.PatientValidator;
import com.upp.apteka.validator.ValidationError;

import javassist.NotFoundException;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private ApplicationContext applicationContext;

	// @Override
	public List<Patient> getAll(int offset, int limit) {
		return patientRepository.getAll(offset, limit);
	}

	// @Override
	public Long create(Patient patient) {
		return patientRepository.create(patient);
	}

	// @Override
	public Patient read(Long key) {
		return patientRepository.read(key);
	}

	// @Override
	public void update(Patient patient) {
		patientRepository.update(patient);
	}

	// @Override
	public boolean delete(Long key) {
		Patient patient = read(key);

		if (patient.getPurchases().size() != 0 || patient.getPrescriptions().size() != 0)
			return false;
		return patientRepository.delete(key);
	}

	// @Override
	public List<Patient> findByQuery(String query, boolean or) {
		return patientRepository.findByQuery(query, or);
	}

	// @Override
	public List<Patient> findByQuery(String query, int offset, int limit, boolean or) {
		return patientRepository.findByQuery(query, offset, limit, or);
	}

	// @Override
	public List<ValidationError> processAdding(Container container) throws NotFoundException {
		PatientDto patientDto = applicationContext.getBean(PatientDto.class);
		patientDto.readFromContext(container);

		PatientValidator patientValidator = applicationContext.getBean(PatientValidator.class);

		List<ValidationError> errors = patientValidator.validate(patientDto);
		patientDto.showErrors(errors, container);

		if (errors.isEmpty())
			create(new Patient(patientDto));

		return errors;
	}

	// @Override
	public List<ValidationError> processEditing(Container container, Long id) throws NotFoundException {
		PatientDto patientDto = applicationContext.getBean(PatientDto.class);
		patientDto.readFromContext(container);

		PatientValidator patientValidator = applicationContext.getBean(PatientValidator.class);

		List<ValidationError> errors = patientValidator.validate(patientDto);
		patientDto.showErrors(errors, container);

		Patient patient = new Patient(patientDto);
		patient.setId(id);

		if (errors.isEmpty())
			update(patient);

		return errors;
	}

	@Override
	public boolean containsNumber(String number) {
		return patientRepository.containsNumber(number);
	}

	@Override
	public int count(String query, boolean or) {
		return patientRepository.count(query, or);
	}

}
