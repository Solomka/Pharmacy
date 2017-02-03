package com.upp.apteka.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Purchase;
import com.upp.apteka.repository.PurchaseRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository
@Transactional
public class PurchaseRepositoryImpl extends AHibernateRepository<Purchase, Long> implements PurchaseRepository{

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

}
