package com.upp.apteka.repository;

import java.util.List;

import com.upp.apteka.bo.Doctor;
import com.upp.apteka.utils.repository.IRepository;

public interface DoctorRepository extends IRepository<Doctor, Long>{
	
	List<Doctor> findByQuery(String surname, boolean or);
	
	List<Doctor> findByQuery(String surname, int offset, int limit, boolean or);

	int count(String query, boolean or);

}
