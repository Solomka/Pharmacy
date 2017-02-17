package com.upp.apteka.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.upp.apteka.bo.Purchase;
import com.upp.apteka.repository.PurchaseRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository
@Transactional
public class PurchaseRepositoryImpl extends AHibernateRepository<Purchase, Long> implements PurchaseRepository{

	private final String patientRestriction = "((SELECT id_patient FROM prescription WHERE id = id_prescr) IN (SELECT id FROM patient WHERE UPPER(name) LIKE UPPER('%?%') OR UPPER(surname) LIKE UPPER('%?%') OR phone LIKE '%?%'))";
	private final String pharmacyRestriction = "(id_pharmacy IN (SELECT id FROM pharmacy WHERE UPPER(name) LIKE UPPER('%?%')))";
	private final String medicineRestriction = "(EXISTS (SELECT * FROM purch_medicine WHERE id_purchase = {alias}.id AND id_medicine IN (SELECT id FROM medicine WHERE UPPER(name) LIKE UPPER('%?%'))))";
	
	@SuppressWarnings("unchecked")
	public List<Purchase> getAll(int offset, int limit) {
		return (List<Purchase>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit).list();
	}

	public Long create(Purchase purchase) {
		return add(purchase);
	}

	public Purchase read(Long key) {
		return get(key);
	}

	public void update(Purchase purchase) {
		updateEntity(purchase);
		
	}

	public boolean delete(Long key) {
		return deleteEntity(key);
	}

	@SuppressWarnings("unchecked")
	//@Override
	public List<Purchase> findByQuery(String query, Date start, Date finish, boolean or, Long number) {
		return createSearchCriteria(query, start, finish, or, number).list();
	}

	@SuppressWarnings("unchecked")
	//@Override
	public List<Purchase> findByQuery(String query, Date start, Date finish, int offset, int limit, boolean or,
			Long number) {
		Criteria criteria = createSearchCriteria(query, start, finish, or, number);
		criteria.setFirstResult(offset);
		criteria.setMaxResults(limit);
		return criteria.list();
	}
	
	private Criteria createSearchCriteria(String query, Date start, Date finish, boolean or, Long number) {
		Criteria criteria = createEntityCriteria();

		Conjunction conjunction = Restrictions.conjunction();

		// Додаємо до КНФ інформацію про дату, якщо вона вказана
		if (start != null && finish != null)
			conjunction.add(Restrictions.between("date", start, finish));

		// Додаємо до КНФ інформацію про номер, якщо він вказаний
		if (number != null)
			conjunction.add(Restrictions.eq("id", number));
		
		// Якщо бодай щось вказано у формі запиту, то розбиваємо і додаємо до
		// частини критеріїв
		if (!StringUtils.isEmptyOrWhitespaceOnly(query)) {

			// Всі підкритерії
			List<Disjunction> restrictions = new ArrayList<Disjunction>();

			String[] subValues = query.split(" ");

			for (String subValue : subValues)
				if (!StringUtils.isEmptyOrWhitespaceOnly(subValue))
					// Додавання обмежень на ліки, пацієна, лікаря та аптеку, де
					// продали бодай щось
					restrictions.add(
							Restrictions.or(Restrictions.sqlRestriction(medicineRestriction.replace("?", subValue)),
									Restrictions.sqlRestriction(pharmacyRestriction.replace("?", subValue)),
									Restrictions.sqlRestriction(patientRestriction.replace("?", subValue))));

			// Збираємо критерії докупи залежно від початкової умови
			if (or) {
				Disjunction disjunction = Restrictions.or();

				for (Disjunction exp : restrictions)
					disjunction.add(exp);

				conjunction.add(disjunction);
			} else {
				Conjunction subConjunction = Restrictions.and();

				for (Disjunction exp : restrictions)
					subConjunction.add(exp);

				conjunction.add(subConjunction);
			}
		}

		criteria.add(conjunction);
		return criteria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Purchase> findByPrescription(Long id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id_prescr", id));
		
		return criteria.list();
	}

}
