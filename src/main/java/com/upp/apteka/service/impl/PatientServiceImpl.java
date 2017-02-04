package com.upp.apteka.service.impl;

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

	@Override
	public List<Patient> getAll(int offset, int limit) {
		return patientRepository.getAll(offset, limit);
	}

	@Override
	public Long create(Patient patient) {
		return patientRepository.create(patient);
	}

	@Override
	public Patient read(Long key) {
		return patientRepository.read(key);
	}

	@Override
	public void update(Patient patient) {
		patientRepository.update(patient);
	}

	@Override
	public boolean delete(Long key) {
		return patientRepository.delete(key);
	}

	@Override
	public List<Patient> findByName(String surname) {
		return patientRepository.findByName(surname);
	}

	@Override
	public List<Patient> findByName(String surname, int offset, int limit) {
		return patientRepository.findByName(surname, offset, limit);
	}

	@Override
	public List<ValidationError> processAdding() throws NotFoundException {
		PatientDto patientDto = applicationContext.getBean(PatientDto.class);
		patientDto.readFromContext();

		PatientValidator patientValidator = applicationContext.getBean(PatientValidator.class);

		List<ValidationError> errors = patientValidator.validate(patientDto);
		patientDto.showErrors(errors);

		if (errors.isEmpty())
			create(new Patient(patientDto));

		return errors;
	}

}
