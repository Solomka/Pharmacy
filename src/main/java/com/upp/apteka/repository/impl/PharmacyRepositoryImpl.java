package com.upp.apteka.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.repository.PharmacyRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository("pharmacyRepository")
@Transactional
public class PharmacyRepositoryImpl extends AHibernateRepository<Pharmacy, Long> implements PharmacyRepository {

	@SuppressWarnings("unchecked")
	public List<Pharmacy> getAll() {
		/*
		 * String hql = "FROM Pharmacy"; return (List<Pharmacy>)
		 * createQuery(hql).list();
		 */
		return (List<Pharmacy>) createEntityCriteria().list();
	}

	public Long create(Pharmacy pharmacy) {

		return add(pharmacy);
	}

	public Pharmacy read(Long key) {

		return get(key);
	}

	public void update(Pharmacy pharmacy) {

		updateEntity(pharmacy);

	}

	public boolean delete(Long key) {

		return deleteEntity(key);
	}

	@SuppressWarnings("unchecked")
	public List<Pharmacy> findPharamcyByName(String name) {

		String hql = "FROM Pharmacy WHERE name = :name";
		Query query = createQuery(hql).setParameter("name", name);
		return (List<Pharmacy>) query.list();
	}
}
