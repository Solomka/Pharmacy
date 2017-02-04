package com.upp.apteka.service;

import java.util.List;

import com.upp.apteka.bo.Patient;
import com.upp.apteka.validator.ValidationError;

import javassist.NotFoundException;

public interface PatientService {
	
	List<ValidationError> processAdding() throws NotFoundException;
	
	List<Patient> getAll(int offset, int limit);
	
	List<Patient> findByName(String surname);
	
	List<Patient> findByName(String surname, int offset, int limit);

	Long create(Patient patient);

	Patient read(Long key);

	void update(Patient patient);

	boolean delete(Long key);
}
