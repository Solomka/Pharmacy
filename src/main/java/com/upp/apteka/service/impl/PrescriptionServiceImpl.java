package com.upp.apteka.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.apteka.bo.Prescription;
import com.upp.apteka.repository.PrescriptionRepository;
import com.upp.apteka.service.PrescriptionService;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Override
	public List<Prescription> getAll(int offset, int limit) {
		return prescriptionRepository.getAll(offset, limit);
	}

	@Override
	public Long create(Prescription prescription) {
		return prescriptionRepository.create(prescription);
	}

	@Override
	public Prescription read(Long key) {
		return prescriptionRepository.read(key);
	}

	@Override
	public void update(Prescription prescription) {
		prescriptionRepository.update(prescription);

	}

	@Override
	public boolean delete(Long key) {
		return prescriptionRepository.delete(key);
	}

	@Override
	public List<Prescription> findByQuery(String query, Date start, Date finishDate, boolean or, Boolean sold) {
		return prescriptionRepository.findByQuery(query, start, finishDate, or, sold);
	}

	@Override
	public List<Prescription> findByQuery(String query, Date start, Date finishDate, int offset, int limit, boolean or,
			Boolean sold) {
		return prescriptionRepository.findByQuery(query, start, finishDate, offset, limit, or, sold);
	}

}
