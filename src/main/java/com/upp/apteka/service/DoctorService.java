package com.upp.apteka.service;

import java.awt.Container;
import java.util.List;

import com.upp.apteka.bo.Doctor;
import com.upp.apteka.validator.ValidationError;

import javassist.NotFoundException;

public interface DoctorService {
	
	List<ValidationError> processAdding(Container container) throws NotFoundException;
	
	List<ValidationError> processEditing(Container container, Long id) throws NotFoundException;
	
	List<Doctor> getAll(int offset, int limit);
	
	List<Doctor> findByQuery(String surname, boolean or);
	
	List<Doctor> findByQuery(String surname, int offset, int limit, boolean or);

	Long create(Doctor doctor);

	Doctor read(Long key);

	void update(Doctor doctor);

	boolean delete(Long key);
}
