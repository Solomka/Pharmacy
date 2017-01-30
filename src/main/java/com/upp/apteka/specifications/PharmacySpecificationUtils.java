package com.upp.apteka.specifications;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.upp.apteka.utils.repository.HibernateSpecification;

/**
 * Pharmacy specification utils class
 * 
 * @author Solomka
 *
 */
public final class PharmacySpecificationUtils {

	private PharmacySpecificationUtils() {
		throw new RuntimeException();
	}

	/**
	 * find Pharmacy by name specification
	 * 
	 * @param name
	 * @return
	 */
	public static HibernateSpecification findPharmacyByName(final String name) {

		return new HibernateSpecification() {

			String pharmacyName;

			{
				pharmacyName = name;
			}

			public Criterion toCriteria() {
				
				/*
				 * Ex1: for simple requests
				 */

				//return Restrictions.eq("name", pharmacyName);
				
				/*
				 * Ex2: for complex requests
				 * 
				 * Same as:
				 * 
				 * SELECT *
				 * FROM Pharmacy
				 * WHERE name = 'Zelena apteka'
				 * 
				 */
				String hql = "name = " + " ' " + pharmacyName + " ' ";
				return Restrictions.sqlRestriction(hql);
			}
		};
	}

}
