package com.upp.apteka.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Delivery;
import com.upp.apteka.utils.repository.AHibernateRepository;
import com.upp.apteka.utils.repository.HqlSpecification;

@Repository("deliveryRepository")
@Transactional
public class DeliveryRepository extends AHibernateRepository<Delivery, Long, HqlSpecification> {

	@SuppressWarnings("unchecked")
	public List<Delivery> getAll() {

		return (List<Delivery>) createEntityCriteria().list();
	}

	public List<Delivery> searchByCriteria(HqlSpecification specification) {

		return findByCriteria(specification.toHql());
	}

	public Long create(Delivery delivery) {

		return add(delivery);
	}

	public Delivery read(Long key) {

		return get(key);
	}

	public void update(Delivery delivery) {

		updateEntity(delivery);
	}

	public boolean delete(Long key) {

		return deleteEntity(key);
	}

}
