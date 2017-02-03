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
	public List<Delivery> getAll(int offset, int limit) {

		return (List<Delivery>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit).list();
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
	public List<Delivery> findPharmacyDeliveriesByPeriod(Date from, Date to, Long pharmacyId, int offset, int limit) {
		/*
		String hql = "FROM Delivery AS d WHERE d.date BETWEEN :from AND :to AND d.pharmacy.id = :pharmacyId";
		Query query = createQuery(hql).setParameter("from", from).setParameter("to", to).setParameter("pharmacyId",
				pharmacyId);
		return (List<Delivery>) query.list();
		*/
		
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.between("date", from, to)).add(Restrictions.eq("pharmacy.id", pharmacyId));
		
		return (List<Delivery>)criteria.setFirstResult(offset).setMaxResults(limit).list();
		
	}

	@SuppressWarnings("unchecked")
	public List<Delivery> findPharmacyMedicineDeliveriesByPeriod(Date from, Date to, Long pharmacyId, Long medicineId, int offset, int limit) {
		
		String hql = "SELECT DISTINCT d"
				+ " FROM Delivery AS d INNER JOIN d.deliveryMedicines AS dm"
				+ " WHERE d.date BETWEEN :from AND :to AND d.pharmacy.id = :pharmacyId AND dm.deliveryMedicineID.medicine.id = :medicineId" ;
			
		Query query = createQuery(hql).setParameter("from", from).setParameter("to", to).setParameter("pharmacyId",
				pharmacyId).setParameter("medicineId", medicineId);
		return (List<Delivery>) query.setFirstResult(offset).setMaxResults(limit).list();
	}

}
