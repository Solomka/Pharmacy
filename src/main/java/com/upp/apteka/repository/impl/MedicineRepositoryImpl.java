package com.upp.apteka.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.repository.MedicineRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository("medicineRepository")
@Transactional
public class MedicineRepositoryImpl extends AHibernateRepository<Medicine, Long> implements MedicineRepository {

	@SuppressWarnings("unchecked")
	public List<Medicine> getAll(int offset, int limit) {

		return (List<Medicine>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit)
				.addOrder(Order.asc("name")).list();
	}

	public Long create(Medicine medicine) {

		return add(medicine);
	}

	public Medicine read(Long key) {

		return get(key);
	}

	public void update(Medicine medicine) {
		if (medicine.getPharmacyMedicines() != null) {

			for (PharmacyMedicine pharmacyMedicine : medicine.getPharmacyMedicines()) {
				getSession().update(pharmacyMedicine);
			}
		}

		updateEntity(medicine);
	}

	public boolean delete(Long key) {

		return deleteEntity(key);
	}

	@SuppressWarnings("unchecked")
	public List<PharmacyMedicine> getPharmacyMedicines(Long id, int offset, int limit) {

		String hql = "SELECT phM" + " FROM PharmacyMedicine phM"
				+ " WHERE phM.pharmacyMedicineID.pharmacy.id = :id AND phM.packQuantity != 0 order by phM.pharmacyMedicineID.medicine.name asc";

		Query query = createQuery(hql).setParameter("id", id);
		return (List<PharmacyMedicine>) query.setFirstResult(offset).setMaxResults(limit).list();
	}

	@SuppressWarnings("unchecked")
	public List<PharmacyMedicine> getPharmacyMedicine(Long pharmacyId, String medicineName, int offset, int limit) {
		String hql = "SELECT phM" + " FROM PharmacyMedicine AS phM"
				+ " WHERE phM.pharmacyMedicineID.pharmacy.id = :pharmacyId AND phM.pharmacyMedicineID.medicine.name LIKE :medicineName order by phM.pharmacyMedicineID.medicine.name asc";

		Query query = createQuery(hql).setParameter("pharmacyId", pharmacyId).setString("medicineName",
				medicineName + "%");
		return (List<PharmacyMedicine>) query.setFirstResult(offset).setMaxResults(limit).list();
	}

	@SuppressWarnings("unchecked")
	public List<PharmacyMedicine> searchMedicineInPharmacies(String medicineName, int offset, int limit) {
		String hql = "SELECT phM" + " FROM PharmacyMedicine AS phM"
				+ " WHERE phM.pharmacyMedicineID.medicine.name LIKE :medicineName AND phM.packQuantity != 0 order by phM.pharmacyMedicineID.medicine.name asc";

		Query query = createQuery(hql).setString("medicineName", medicineName + "%");
		return (List<PharmacyMedicine>) query.setFirstResult(offset).setMaxResults(limit).list();
	}

	@SuppressWarnings("unchecked")
	// @Override
	public List<Medicine> findByNameOrProducer(String query) {
		Criteria criteria = createEntityCriteria();

		String[] names = query.split(" ");

		List<Disjunction> restrictions = new ArrayList<Disjunction>();

		for (String name : names)
			if (!StringUtils.isEmptyOrWhitespaceOnly(name)) {
				Disjunction dis = Restrictions.disjunction();
				dis.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
				dis.add(Restrictions.ilike("producer", name, MatchMode.ANYWHERE));

				restrictions.add(dis);
			}

		if (restrictions.size() > 0) {
			Disjunction disjunction = Restrictions.disjunction();

			for (Disjunction dis : restrictions)
				disjunction.add(dis);

			criteria.add(disjunction);
		}

		return criteria.list();
	}

	public boolean containsNameProducerMedicine(String name, String producer) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name)).add(Restrictions.eqOrIsNull("producer", producer));
		return !criteria.list().isEmpty();
	}

	public int count() {

		String hql = "SELECT COUNT(*) FROM Medicine";
		Query query = createQuery(hql);
		// Used to specify that the query results will be a projection (scalar
		// in nature).
		// Criteria criteria = createEntityCriteria();
		// return
		// (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
		return ((Long) query.iterate().next()).intValue();

	}

	/*
	 * queries for general medicines
	 */

	@SuppressWarnings("unchecked")
	public List<Medicine> findByQuery(String query, boolean or) {
		Criteria criteria = prepareQueryStatement(query, or);
		return (List<Medicine>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Medicine> findByQuery(String query, int offset, int limit, boolean or) {

		Criteria criteria = prepareQueryStatement(query, or).setFirstResult(offset).setMaxResults(limit);
		return (List<Medicine>) criteria.list();
	}

	public int count(String query, boolean or) {
		return ((Number) prepareQueryStatement(query, or).setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
	}

	/*
	 * PREPARE QUERY STATEMENT
	 */

	/*
	 * query == "Mezum"
	 */
	private Criteria prepareQueryStatement(String query, boolean or) {

		// check if query filter exists
		if (query == null) {
			query = "";
		}

		String[] searchValues = query.split(" ");

		/*
		 * execute basic getAll request with pagination and ordering
		 */

		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("name"));

		// add restrictions

		List<Disjunction> restrictions = new ArrayList<Disjunction>();

		for (String sValue : searchValues) {
			if (!StringUtils.isEmptyOrWhitespaceOnly(sValue)) {
				Disjunction disj = Restrictions.disjunction();
				disj.add(Restrictions.ilike("name", sValue, MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("producer", sValue, MatchMode.ANYWHERE));

				restrictions.add(disj);
			}
		}

		/*
		 * if any restriction exists
		 */

		if (or && restrictions.size() > 0) {

			Disjunction disjunction = Restrictions.disjunction();

			for (Disjunction disj : restrictions) {
				disjunction.add(disj);

			}

			criteria.add(disjunction);
		} else if (restrictions.size() > 0) {

			Conjunction conjunction = Restrictions.conjunction();

			for (Disjunction disj : restrictions) {
				conjunction.add(disj);
			}
			criteria.add(conjunction);

		}

		return criteria;
	}

}
