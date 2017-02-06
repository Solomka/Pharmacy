package com.upp.apteka.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Prescription;
import com.upp.apteka.repository.PrescriptionRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository
@Transactional
public class PrescriptionRepositoryImpl extends AHibernateRepository<Prescription, Long> implements PrescriptionRepository{

	@SuppressWarnings("unchecked")
	public List<Prescription> getAll(int offset, int limit) {
		return (List<Prescription>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit).list();
	}

	public Long create(Prescription prescription) {
		return add(prescription);
	}

	public Prescription read(Long key) {
		return get(key);
	}

	public void update(Prescription prescription) {
		updateEntity(prescription);
		
	}

	public boolean delete(Long key) {
		return deleteEntity(key);
	}

}
