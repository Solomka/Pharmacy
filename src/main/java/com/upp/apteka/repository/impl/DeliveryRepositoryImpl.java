package com.upp.apteka.repository.impl;

import java.sql.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Delivery;
import com.upp.apteka.repository.DeliveryRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository("deliveryRepository")
@Transactional
public class DeliveryRepositoryImpl extends AHibernateRepository<Delivery, Long> implements DeliveryRepository {

	@SuppressWarnings("unchecked")
	public List<Delivery> getAll() {

		return (List<Delivery>) createEntityCriteria().list();
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

	@SuppressWarnings("unchecked")
	public List<Delivery> findDeliveryByPeriodAndPharmacy(Date from, Date to, Long pharmacyId) {
		String hql = "FROM Delivery WHERE date BETWEEN :from AND :to AND id_pharmacy = :pharmacyId";
		Query query = createQuery(hql).setParameter("from", from).setParameter("to", to).setParameter("pharmacyId",
				pharmacyId);
		return (List<Delivery>) query.list();

	}

}
