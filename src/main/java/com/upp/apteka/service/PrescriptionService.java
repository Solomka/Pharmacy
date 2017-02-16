package com.upp.apteka.service;

import java.sql.Date;
import java.util.List;

import com.upp.apteka.bo.Prescription;
import com.upp.apteka.dto.ChooseMedicineDto;

public interface PrescriptionService {
	
	Long create(Long doctorId, Long patientId, Date date, List<ChooseMedicineDto> dtos);
	
	List<Prescription> getAll(int offset, int limit);
	
	List<Prescription> findByQuery(String query, Date start, Date finishDate, boolean or, Boolean sold);
	
	List<Prescription> findByQuery(String query, Date start, Date finishDate, int offset, int limit, boolean or, Boolean sold);

	Long create(Prescription prescription);

	Prescription read(Long key);

	void update(Prescription prescription);

	boolean delete(Long key);
	
	List<Prescription> getUnboughtPrescriptions(Long customerId);
}
