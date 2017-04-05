package com.upp.apteka.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.upp.apteka.bo.Patient;
import com.upp.apteka.repository.PatientRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository
@Transactional
public class PatientRepositoryImpl extends AHibernateRepository<Patient, Long> implements PatientRepository {

	@SuppressWarnings("unchecked")
	public List<Patient> getAll(int offset, int limit) {
		return (List<Patient>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit).list();
	}

	public Long create(Patient patient) {
		return add(patient);
	}

	public Patient read(Long key) {
		return get(key);
	}

	public void update(Patient patient) {
		updateEntity(patient);

	}

	public boolean delete(Long key) {
		return deleteEntity(key);
	}

	@SuppressWarnings("unchecked")
	// @Override
	public List<Patient> findByQuery(String query, boolean or) {

		Criteria criteria = prepare(query, or);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	// @Override
	public List<Patient> findByQuery(String query, int offset, int limit, boolean or) {

		Criteria criteria = prepare(query, or).setFirstResult(offset).setMaxResults(limit);
		return criteria.list();
	}

	// @Override
	public boolean containsNumber(String number) {
		Criteria criteria = createEntityCriteria();

		criteria.add(Restrictions.eq("phone", number));
		return 1 == criteria.list().size();
	}

	// @Override
	public int count(String query, boolean or) {
		return ((Number) prepare(query, or).setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	/*
	 * PREPARE QUERY STATEMENT
	 */

	private Criteria prepare(String query, boolean or) {
		if (query == null)
			query = "";

		String[] names = query.split(" ");
		for (int i = 0; i < names.length; ++i) {
			System.out.println("Prepare name: " + names[i]);
		}

		/*
		 * execute basic getAll with pagination request
		 */

		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("surname"));

		List<Disjunction> restrictions = new ArrayList<Disjunction>();

		for (String name : names)
			if (!StringUtils.isEmptyOrWhitespaceOnly(name))
				restrictions.add(Restrictions.or(Restrictions.ilike("surname", name, MatchMode.ANYWHERE),
						Restrictions.ilike("name", name, MatchMode.ANYWHERE),
						Restrictions.ilike("phone", name, MatchMode.ANYWHERE)));

		System.out.println("disjunctions set size: " + restrictions.size());

		/*
		 * if any restriction exists
		 */
		if (or && restrictions.size() > 0) {

			/*
			 * Hibernate Disjunction, is used to add multiple condition in SQL
			 * query separated by OR clause within brackets. To generate
			 * following query using Hibernate Criteria we need to use
			 * Disjunction.
			 */

			Disjunction disjunction = Restrictions.disjunction();

			for (Disjunction dis : restrictions)
				disjunction.add(dis);

			criteria.add(disjunction);
		} else if (restrictions.size() > 0) {

			/*
			 * Hibernate Conjunction, is used to add multiple condition in SQL
			 * query separated by AND clause within brackets. To generate
			 * following query using Hibernate Criteria we need to use
			 * Conjunction.
			 */

			Conjunction conjunction = Restrictions.conjunction();

			for (Disjunction dis : restrictions)
				conjunction.add(dis);

			criteria.add(conjunction);
		}
		return criteria;
	}
}
