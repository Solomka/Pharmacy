package com.upp.apteka.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.upp.apteka.bo.Medicine;
import com.upp.apteka.repository.MedicineRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository("medicineRepository")
@Transactional
public class MedicineRepositoryImpl extends AHibernateRepository<Medicine, Long> implements MedicineRepository {

	@SuppressWarnings("unchecked")
	public List<Medicine> getAll(int offset, int limit) {

		return (List<Medicine>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit).list();
	}

	public Long create(Medicine medicine) {

		return add(medicine);
	}

	public Medicine read(Long key) {

		return get(key);
	}

	public void update(Medicine medicine) {

		updateEntity(medicine);

	}

	public boolean delete(Long key) {

		return deleteEntity(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicine> findByNameOrProducer(String query) {
		Criteria criteria = createEntityCriteria();
		
		String[] names = query.split(" ");

		List<Disjunction> restrictions = new ArrayList<>();

		for (String name : names)
			if (!StringUtils.isEmptyOrWhitespaceOnly(name)){
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

}
