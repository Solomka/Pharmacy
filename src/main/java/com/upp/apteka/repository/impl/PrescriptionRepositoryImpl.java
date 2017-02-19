package com.upp.apteka.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.mysql.jdbc.StringUtils;
import com.upp.apteka.bo.Prescription;
import com.upp.apteka.bo.PrescriptionMedicine;
import com.upp.apteka.repository.PrescriptionRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository
public class PrescriptionRepositoryImpl extends AHibernateRepository<Prescription, Long>
		implements PrescriptionRepository {

	private final String patientRestriction = "(id_patient IN (SELECT id FROM patient WHERE UPPER(name) LIKE UPPER('%?%') OR UPPER(surname) LIKE UPPER('%?%') OR phone LIKE '%?%'))";
	private final String doctorRestriction = "(id_doctor IN (SELECT id FROM doctor WHERE UPPER(name) LIKE UPPER('%?%') OR UPPER(surname) LIKE UPPER('%?%')))";
	private final String pharmacyRestriction = "(EXISTS (SELECT * FROM purchase WHERE id_prescr = id AND id_pharmacy IN (SELECT id FROM pharmacy WHERE UPPER(name) LIKE UPPER('%?%'))))";
	private final String medicineRestriction = "(EXISTS (SELECT * FROM prescr_medicine WHERE id_prescr = id AND id_medicine IN (SELECT id FROM medicine WHERE UPPER(name) LIKE UPPER('%?%'))))";

	private final String SOLD = "(NOT EXISTS (SELECT * FROM prescr_medicine pm WHERE pack_bought < pack_quantity AND id_prescr = id))";
	private final String ACTUAL = "(EXISTS (SELECT * FROM prescr_medicine pm WHERE pack_bought < pack_quantity AND id_prescr = id))";

	@SuppressWarnings("unchecked")
	public List<Prescription> getAll(int offset, int limit) {
		return (List<Prescription>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit).list();
	}

	public Long create(Prescription prescription) {
		return add(prescription);
	}

	public Prescription read(Long key) {
		return get(key);
	}

	public void update(Prescription prescription) {

		for (PrescriptionMedicine prescriptionMedicine : prescription.getPrescriptionMedicines()){
			System.out.println(prescriptionMedicine.getPackBought());
			getSession().update(prescriptionMedicine);
			
		}

		updateEntity(prescription);

	}

	public boolean delete(Long key) {
		return deleteEntity(key);
	}

	@SuppressWarnings("unchecked")
	// @Override
	public List<Prescription> findByQuery(String query, Date start, Date finish, boolean or, Boolean sold) {
		return createSearchCriteria(query, start, finish, or, sold).list();
	}

	@SuppressWarnings("unchecked")
	// @Override
	public List<Prescription> findByQuery(String query, Date start, Date finish, int offset, int limit, boolean or,
			Boolean sold) {
		Criteria criteria = createSearchCriteria(query, start, finish, or, sold);
		criteria.setFirstResult(offset);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	private Criteria createSearchCriteria(String query, Date start, Date finish, boolean or, Boolean sold) {
		Criteria criteria = createEntityCriteria();

		Conjunction conjunction = Restrictions.conjunction();

		// Додаємо до КНФ інформацію про дату, якщо вона вказана
		if (start != null && finish != null)
			conjunction.add(Restrictions.between("date", start, finish));

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
							Restrictions.or(Restrictions.sqlRestriction(pharmacyRestriction.replace("?", subValue)),
									Restrictions.sqlRestriction(doctorRestriction.replace("?", subValue)),
									Restrictions.sqlRestriction(patientRestriction.replace("?", subValue)),
									Restrictions.sqlRestriction(medicineRestriction.replace("?", subValue))));

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

		if (sold != null && sold)
			conjunction.add(Restrictions.sqlRestriction(SOLD));

		if (sold != null && !sold)
			conjunction.add(Restrictions.sqlRestriction(ACTUAL));

		criteria.add(conjunction);
		return criteria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Prescription> getUnboughtPrescriptions(Long customerId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.sqlRestriction("id_patient = " + customerId));
		criteria.add(Restrictions.sqlRestriction(ACTUAL));

		return (List<Prescription>) criteria.list();
	}

}
