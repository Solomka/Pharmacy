package com.upp.apteka.repository;

import java.util.List;

import com.upp.apteka.bo.Patient;
import com.upp.apteka.utils.repository.IRepository;

public interface PatientRepository extends IRepository<Patient, Long>{

	List<Patient> findByQuery(String surname, boolean or);
	
	List<Patient> findByQuery(String surname, int offset, int limit, boolean or);
	
	boolean containsNumber(String number);
}
