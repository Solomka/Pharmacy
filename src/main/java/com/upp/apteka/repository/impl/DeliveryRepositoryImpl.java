package com.upp.apteka.repository.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.StringUtils;
import com.upp.apteka.bo.Delivery;
import com.upp.apteka.bo.DeliveryMedicine;
import com.upp.apteka.repository.DeliveryRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository("deliveryRepository")
public class DeliveryRepositoryImpl extends AHibernateRepository<Delivery, Long> implements DeliveryRepository {
	
	private final String medicineNameRestriction = "({alias}.id IN "
												 				+ "(SELECT id_delivery"
												 				+ " FROM delivery_medicine"
												 				+ " WHERE id_medicine IN (SELECT id"
												 									   + " FROM medicine"									
												 									   + " WHERE UPPER(name) LIKE UPPER('%?%'))))";
	private final String medicineProducerRestriction = "({alias}.id IN "
																+ "(SELECT id_delivery"
																+ " FROM delivery_medicine"
																+ " WHERE id_medicine IN (SELECT id "
																					   + " FROM medicine"									
																					   + " WHERE UPPER(producer) LIKE UPPER('%?%'))))";

	@SuppressWarnings("unchecked")
	public List<Delivery> getAll(int offset, int limit) {

		return (List<Delivery>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit).addOrder(Order.desc("date")).list();
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
	public List<Delivery> findPharmacyDeliveries(Long pharmacyId, int offset, int limit) {
		
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("pharmacy.id", pharmacyId));
		return (List<Delivery>) criteria.setFetchSize(offset).setMaxResults(limit).addOrder(Order.desc("date")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Delivery> findPharmacyDeliveriesByPeriod(Date from, Date to, Long pharmacyId, int offset, int limit) {
		/*
		 * String hql =
		 * "FROM Delivery AS d WHERE d.date BETWEEN :from AND :to AND d.pharmacy.id = :pharmacyId"
		 * ; Query query = createQuery(hql).setParameter("from",
		 * from).setParameter("to", to).setParameter("pharmacyId", pharmacyId);
		 * return (List<Delivery>) query.list();
		 */

		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.between("date", from, to)).add(Restrictions.eq("pharmacy.id", pharmacyId));

		return (List<Delivery>) criteria.setFirstResult(offset).setMaxResults(limit).addOrder(Order.desc("date")).list();

	}

	@SuppressWarnings("unchecked")
	public List<Delivery> findPharmacyMedicineDeliveriesByPeriod(Date from, Date to, Long pharmacyId, Long medicineId,
			int offset, int limit) {

		String hql = "SELECT DISTINCT d" + " FROM Delivery AS d INNER JOIN d.deliveryMedicines AS dm"
				+ " WHERE d.date BETWEEN :from AND :to AND d.pharmacy.id = :pharmacyId AND dm.deliveryMedicineID.medicine.id = :medicineId"
				+ " order by date desc";

		Query query = createQuery(hql).setParameter("from", from).setParameter("to", to)
				.setParameter("pharmacyId", pharmacyId).setParameter("medicineId", medicineId);
		return (List<Delivery>) query.setFirstResult(offset).setMaxResults(limit).list();
	}
	
	 public boolean checkIfDeliveryMedicineSold(Long deliveryId){
		 
		 String hql = " SELECT p"
		 		+ " FROM Purchase AS p"
		 		+ " WHERE p.date >= (SELECT d.date"
		 		+ 					" FROM Delivery AS d"
		 		+ 					" WHERE d.id = :deliveryId)"
		 		+ " AND p.id IN (SELECT p1.purchMedicine.purchase.id"
		 		+ 				" FROM p.purchaseMedicines AS p1"
		 		+ 				" WHERE p1.purchMedicine.medicine.id IN "
		 		+ 										  " (SELECT dm.deliveryMedicineID.medicine.id"
		 		+ 											" FROM DeliveryMedicine AS dm"
		 		+ 											" WHERE dm.deliveryMedicineID.delivery.id = :deliveryId))";
		 
		 Query query = createQuery(hql).setParameter("deliveryId", deliveryId);
		 
		 return query.list().size() != 0;
	 }

	 @SuppressWarnings("unchecked")
	public List<DeliveryMedicine> getDeliveryMedicines(Long deliveryId, int offset, int limit){
		 
		String hql ="SELECT dM"
					+ " FROM DeliveryMedicine AS dM"
					+ " WHERE dM.deliveryMedicineID.delivery.id = :deliveryId order by dM.deliveryMedicineID.medicine.name asc";
			
		Query query = createQuery(hql).setParameter("deliveryId", deliveryId);
		return  (List<DeliveryMedicine>) query.setFirstResult(offset).setMaxResults(limit).list();
	 }
	 
	 public int countDM(Long deliveryId) {
			
		 String hql ="SELECT dM"
					+ " FROM DeliveryMedicine AS dM"
					+ " WHERE dM.deliveryMedicineID.delivery.id = :deliveryId order by dM.deliveryMedicineID.medicine.name asc";
			
		Query query = createQuery(hql).setParameter("deliveryId", deliveryId);
		return  (int) query.list().size();
		}

	@SuppressWarnings("unchecked")
	public List<Delivery> findByQuery(String query, Date start, Date finish, int offset, int limit, boolean or) {
		
		Criteria criteria = prepareQueryStatement(query, start, finish, or).setFirstResult(offset).setMaxResults(limit);
		return (List<Delivery>) criteria.list();
	}

	public int count(String query, Date start, Date finish, boolean or) {
		
		return ((Number)prepareQueryStatement(query, start, finish, or).setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
	}
	
	private Criteria prepareQueryStatement(String query, Date start, Date finish, boolean or) {
		
		/*
		 * create general criteria with order and pagination
		 */
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.desc("date"));
		
		Conjunction conjunction = Restrictions.conjunction();
		List<Disjunction> restrictions = new ArrayList<Disjunction>();

		/*
		 * add date if exists
		 */
		if (start != null && finish != null)
			conjunction.add(Restrictions.between("date", start, finish));

		
		/*
		 * add search filters
		 */
		if (!StringUtils.isEmptyOrWhitespaceOnly(query)) {			

			String[] subValues = query.split(" ");

			for (String subValue : subValues)
				if (!StringUtils.isEmptyOrWhitespaceOnly(subValue))
					
					try {
					Long id = Long.valueOf(subValue);
					restrictions.add(Restrictions.or(Restrictions.eq("id", id)));
					} catch (Exception e) {
						Disjunction disj = Restrictions.disjunction();
						// Match the pattern anywhere in the string
						disj.add(Restrictions.sqlRestriction(medicineNameRestriction.replace("?", subValue)));
						disj.add(Restrictions.sqlRestriction(medicineProducerRestriction.replace("?", subValue)));
						
						restrictions.add(disj);
					}

			if (or) {
				Disjunction disjunction = Restrictions.disjunction();

				for (Disjunction exp : restrictions)
					disjunction.add(exp);

				conjunction.add(disjunction);
			} else {
				Conjunction subConjunction = Restrictions.conjunction();

				for (Disjunction exp : restrictions)
					subConjunction.add(exp);

				conjunction.add(subConjunction);
			}
		}

		criteria.add(conjunction);
		return criteria;
	}	

}
