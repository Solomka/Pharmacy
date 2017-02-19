package com.upp.apteka.repository;

import java.sql.Date;
import java.util.List;

import com.upp.apteka.bo.Purchase;
import com.upp.apteka.utils.repository.IRepository;

public interface PurchaseRepository extends IRepository<Purchase, Long>{

	List<Purchase> findByQuery(String query, Date start, Date finishDate, boolean or, Long number);
	
	List<Purchase> findByQuery(String query, Date start, Date finishDate, int offset, int limit, boolean or, Long number);

	List<Purchase> findByPrescription(Long id);

	int count(String query, Date start, Date finish, boolean or, Long number);
}
