package com.upp.apteka.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.upp.apteka.bo.Patient;
import com.upp.apteka.repository.PatientRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository
@Transactional
public class PatientRepositoryImpl extends AHibernateRepository<Patient, Long> implements PatientRepository {

	@SuppressWarnings("unchecked")
	public List<Patient> getAll(int offset, int limit) {
		return (List<Patient>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit).list();
	}

	public Long create(Patient patient) {
		return add(patient);
	}

	public Patient read(Long key) {
		return get(key);
	}

	public void update(Patient patient) {
		updateEntity(patient);

	}

	public boolean delete(Long key) {
		return deleteEntity(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> findByName(String surname) {

		if (surname == null)
			throw new NullPointerException("Surname can not be null");

		String[] names = surname.split(" ");
		Criteria criteria = createEntityCriteria();

		for (String name : names)
			if (!StringUtils.isEmptyOrWhitespaceOnly(name))
				criteria.add(Restrictions.or(Restrictions.ilike("surname", name), Restrictions.ilike("name", name)));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> findByName(String surname, int offset, int limit) {
		if (surname == null)
			throw new NullPointerException("Surname can not be null");

		String[] names = surname.split(" ");
		Criteria criteria = createEntityCriteria().setFirstResult(offset).setMaxResults(limit);

		for (String name : names)
			if (!StringUtils.isEmptyOrWhitespaceOnly(name))
				criteria.add(Restrictions.or(Restrictions.ilike("surname", name), Restrictions.ilike("name", name)));

		return criteria.list();
	}
}
