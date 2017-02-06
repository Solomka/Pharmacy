package com.upp.apteka.service;

import java.util.Date;
import java.util.List;

import com.upp.apteka.bo.Purchase;

public interface PurchaseService {

	List<Purchase> getAll(int offset, int limit);
	
	List<Purchase> findByQuery(String query, Date start, Date finish, boolean or, Long number);
	
	List<Purchase> findByQuery(String query, Date start, Date finish, int offset, int limit, boolean or, Long number);

	Long create(Purchase purchase);

	Purchase read(Long key);

	void update(Purchase purchase);

	boolean delete(Long key);
}
