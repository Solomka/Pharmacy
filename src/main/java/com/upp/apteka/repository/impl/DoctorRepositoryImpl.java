package com.upp.apteka.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
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
	//@Override
	public List<Doctor> findByQuery(String query, boolean or) {

		if (query == null)
			throw new NullPointerException("Surname can not be null");

		String[] names = query.split(" ");
		Criteria criteria = createEntityCriteria();

		List<Disjunction> restrictions = new ArrayList<Disjunction>();

		for (String name : names)
			if (!StringUtils.isEmptyOrWhitespaceOnly(name))
				restrictions.add(Restrictions.or(Restrictions.ilike("surname", name), Restrictions.ilike("name", name),
						Restrictions.ilike("occupation", name)));

		if (or && restrictions.size() > 0){
			Disjunction disjunction = Restrictions.disjunction();
			
			for(Disjunction dis: restrictions)
				disjunction.add(dis);
			
			criteria.add(disjunction);
		}else if (restrictions.size() > 0){
			Conjunction conjunction = Restrictions.conjunction();
			
			for(Disjunction dis: restrictions)
				conjunction.add(dis);
			
			criteria.add(conjunction);
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	//@Override
	public List<Doctor> findByQuery(String query, int offset, int limit, boolean or) {

		if (query == null)
			throw new NullPointerException("Surname can not be null");

		String[] names = query.split(" ");
		Criteria criteria = createEntityCriteria().setFirstResult(offset).setMaxResults(limit);

		List<Disjunction> restrictions = new ArrayList<Disjunction>();

		for (String name : names)
			if (!StringUtils.isEmptyOrWhitespaceOnly(name))
				restrictions.add(Restrictions.or(Restrictions.ilike("surname", name), Restrictions.ilike("name", name),
						Restrictions.ilike("occupation", name)));

		if (or && restrictions.size() > 0){
			Disjunction disjunction = Restrictions.disjunction();
			
			for(Disjunction dis: restrictions)
				disjunction.add(dis);
			
			criteria.add(disjunction);
		}else if (restrictions.size() > 0){
			Conjunction conjunction = Restrictions.conjunction();
			
			for(Disjunction dis: restrictions)
				conjunction.add(dis);
			
			criteria.add(conjunction);
		}
		return criteria.list();
	}

}
