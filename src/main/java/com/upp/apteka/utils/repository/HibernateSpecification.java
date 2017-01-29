package com.upp.apteka.utils.repository;

import org.hibernate.criterion.Criterion;

/**
 * base interface for all request filters
 * 
 * @author Solomka
 *
 */
public interface HibernateSpecification {
	
	Criterion toCriteria();

}
