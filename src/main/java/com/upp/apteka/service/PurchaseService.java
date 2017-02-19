package com.upp.apteka.service;

import java.sql.Date;
import java.util.List;

import com.upp.apteka.bo.Patient;
import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.bo.Prescription;
import com.upp.apteka.bo.Purchase;
import com.upp.apteka.dto.PurchaseMedicineDto;

public interface PurchaseService {

	List<Purchase> getAll(int offset, int limit);
	
	List<Purchase> findByQuery(String query, Date start, Date finish, boolean or, Long number);
	
	List<Purchase> findByQuery(String query, Date start, Date finish, int offset, int limit, boolean or, Long number);

	Long create(Purchase purchase);

	Purchase read(Long key);

	void update(Purchase purchase);

	boolean delete(Long key);
	
	List<Purchase> findByPrescription(Long id);

	void create(Patient patient, Pharmacy pharmacy, Prescription prescription, List<PurchaseMedicineDto> forms);

	void update(Long id, Patient patient, Pharmacy pharmacy, Prescription prescription,
			List<PurchaseMedicineDto> purchaseMedicinesDto);
}
