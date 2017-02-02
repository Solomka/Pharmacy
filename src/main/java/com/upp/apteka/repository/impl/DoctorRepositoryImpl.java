package com.upp.apteka.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Doctor;
import com.upp.apteka.repository.DoctorRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository
@Transactional
public class DoctorRepositoryImpl extends AHibernateRepository<Doctor, Long> implements DoctorRepository{

	public List<Doctor> getAll() {
		return (List<Doctor>) createEntityCriteria().list();
	}

	public Long create(Doctor doctor) {
		return add(doctor);
	}

	public Doctor read(Long key) {
		return get(key);
	}

	public void update(Doctor doctor) {
		updateEntity(doctor);
		
	}

	public boolean delete(Long key) {
		return deleteEntity(key);
	}

}
