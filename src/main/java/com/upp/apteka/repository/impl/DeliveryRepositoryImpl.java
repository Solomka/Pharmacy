package com.upp.apteka.repository.impl;

import java.sql.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
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
	public List<Delivery> findPharmacyDeliveriesByPeriod(Date from, Date to, Long pharmacyId) {
		/*
		String hql = "FROM Delivery WHERE date BETWEEN :from AND :to AND id_pharmacy = :pharmacyId";
		Query query = createQuery(hql).setParameter("from", from).setParameter("to", to).setParameter("pharmacyId",
				pharmacyId);
		return (List<Delivery>) query.list();
		*/
		
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.between("date", from, to)).add(Restrictions.eq("pharmacy.id", pharmacyId));
		
		return (List<Delivery>)criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Delivery> findPharmacyMedicineDeliveriesByPeriod(Date from, Date to, Long pharmacyId, Long medicineId) {
		String hql = "FROM Delivery WHERE date BETWEEN :from AND :to AND id_pharmacy = :pharmacyId";
		Query query = createQuery(hql).setParameter("from", from).setParameter("to", to).setParameter("pharmacyId",
				pharmacyId);
		return (List<Delivery>) query.list();
	}

}
