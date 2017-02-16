package com.upp.apteka.service;

import java.awt.Container;
import java.util.List;

import com.upp.apteka.bo.Patient;
import com.upp.apteka.validator.ValidationError;

import javassist.NotFoundException;

public interface PatientService {
	
	List<ValidationError> processAdding(Container container) throws NotFoundException;
	
	List<ValidationError> processEditing(Container container, Long id) throws NotFoundException;
	
	List<Patient> getAll(int offset, int limit);
	
	List<Patient> findByQuery(String query, boolean or);
	
	List<Patient> findByQuery(String query, int offset, int limit, boolean or);

	Long create(Patient patient);

	Patient read(Long key);

	void update(Patient patient);

	boolean delete(Long key);
	
	boolean containsNumber(String number);
}
