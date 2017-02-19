package com.upp.apteka.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.upp.apteka.bo.Doctor;
import com.upp.apteka.repository.DoctorRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository
@Transactional
public class DoctorRepositoryImpl extends AHibernateRepository<Doctor, Long> implements DoctorRepository {

	@SuppressWarnings("unchecked")
	public List<Doctor> getAll(int offset, int limit) {
		return (List<Doctor>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit).list();
	}

	public Long create(Doctor doctor) {
		return add(doctor);
	}

	public Doctor read(Long key) {
		return get(key);
	}

	public void update(Doctor doctor) {
		updateEntity(doctor);

	}

	public boolean delete(Long key) {
		return deleteEntity(key);
	}

	@SuppressWarnings("unchecked")
	// @Override
	public List<Doctor> findByQuery(String query, boolean or) {

		Criteria criteria = prepare(query, or);
		return criteria.list();
	}

	private Criteria prepare(String query, boolean or) {
		if (query == null)
			query = "";

		String[] names = query.split(" ");
		Criteria criteria = createEntityCriteria();

		List<Disjunction> restrictions = new ArrayList<Disjunction>();

		for (String name : names)
			if (!StringUtils.isEmptyOrWhitespaceOnly(name))
				restrictions.add(Restrictions.or(Restrictions.ilike("surname", name, MatchMode.ANYWHERE),
						Restrictions.ilike("name", name, MatchMode.ANYWHERE),
						Restrictions.ilike("occupation", name, MatchMode.ANYWHERE)));

		if (or && restrictions.size() > 0) {
			Disjunction disjunction = Restrictions.disjunction();

			for (Disjunction dis : restrictions)
				disjunction.add(dis);

			criteria.add(disjunction);
		} else if (restrictions.size() > 0) {
			Conjunction conjunction = Restrictions.conjunction();

			for (Disjunction dis : restrictions)
				conjunction.add(dis);

			criteria.add(conjunction);
		}

		return criteria;
	}

	@SuppressWarnings("unchecked")
	// @Override
	public List<Doctor> findByQuery(String query, int offset, int limit, boolean or) {

		Criteria criteria = prepare(query, or).setFirstResult(offset).setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public int count(String query, boolean or) {
		return ((Number) prepare(query, or).setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

}
