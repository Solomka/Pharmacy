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

import com.mysql.jdbc.StringUtils;
import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.repository.PharmacyRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository("pharmacyRepository")
public class PharmacyRepositoryImpl extends AHibernateRepository<Pharmacy, Long> implements PharmacyRepository {

	@SuppressWarnings("unchecked")
	public List<Pharmacy> getAll(int offset, int limit) {
		/*
		 * String hql = "FROM Pharmacy"; return (List<Pharmacy>)
		 * createQuery(hql).list();
		 */
		return (List<Pharmacy>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit)
				.addOrder(Order.asc("name")).list();
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
	public List<Pharmacy> findPharmacyByName(String name) {

		String hql = "FROM Pharmacy WHERE name LIKE :name";
		// Query query = createQuery(hql).setString("name", + "%" + name + "%");
		Query query = createQuery(hql).setString("name", name + "%");
		return (List<Pharmacy>) query.list();

		/*
		 * Criteria criteria = createEntityCriteria();
		 * criteria.add(Restrictions.eq("name", name)); return (List<Pharmacy>)
		 * criteria.list();
		 */

	}

	public boolean containsAddress(String address) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("address", address));
		return !criteria.list().isEmpty();
	}

	@SuppressWarnings("unchecked")
	public List<Pharmacy> findByQuery(String query, boolean or) {

		Criteria criteria = prepareQueryStatement(query, or);
		return (List<Pharmacy>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Pharmacy> findByQuery(String query, int offset, int limit, boolean or) {

		Criteria criteria = prepareQueryStatement(query, or).setFirstResult(offset).setMaxResults(limit);
		return (List<Pharmacy>) criteria.list();
	}

	public int count(String query, boolean or) {
		((Number) prepareQueryStatement(query, or).setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return ((Number) prepareQueryStatement(query, or).setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
	}

	/*
	 * PREPARE QUERY STATEMENT
	 */

	/*
	 * query == "Zelena apteka"
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
				disj.add(Restrictions.ilike("address", sValue, MatchMode.ANYWHERE));

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
