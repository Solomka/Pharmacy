package com.upp.apteka.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.utils.repository.AHibernateRepository;
import com.upp.apteka.utils.repository.HibernateSpecification;

@Repository("pharmacyRepository")
@Transactional
public class PharmacyRepository extends AHibernateRepository<Pharmacy, Long, HibernateSpecification> {

	@SuppressWarnings("unchecked")
	public List<Pharmacy> getAll() {
		/*
		 * String hql = "FROM Pharmacy"; return (List<Pharmacy>)
		 * createQuery(hql).list();
		 */
		return (List<Pharmacy>) createEntityCriteria().list();
	}

	public List<Pharmacy> searchByCriteria(HibernateSpecification specification) {

		return findByCriteria(specification.toCriteria());
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

}
