package com.upp.apteka.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.utils.repository.AHibernateRepository;
import com.upp.apteka.utils.repository.HibernateSpecification;

@Repository("medicineRepository")
public class MedicineRepository extends AHibernateRepository<Medicine, Long, HibernateSpecification> {

	public List<Medicine> searchByCriteria(HibernateSpecification specification) {

		return findByCriteria(specification.toCriteria());
	}

	public Long create(Medicine medicine) {

		return add(medicine);
	}

	public Medicine read(Long key) {

		return get(key);
	}

	public void update(Medicine medicine) {

		updateEntity(medicine);

	}

	public boolean delete(Long key) {

		return delete(key);
	}

}
