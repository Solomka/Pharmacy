package com.upp.apteka.service;

import java.util.Date;
import java.util.List;

import com.upp.apteka.bo.Prescription;

public interface PrescriptionService {
	
	List<Prescription> getAll(int offset, int limit);
	
	List<Prescription> findByQuery(String query, Date start, Date finishDate, boolean or, Boolean sold);
	
	List<Prescription> findByQuery(String query, Date start, Date finishDate, int offset, int limit, boolean or, Boolean sold);

	Long create(Prescription prescription);

	Prescription read(Long key);

	void update(Prescription prescription);

	boolean delete(Long key);
}
