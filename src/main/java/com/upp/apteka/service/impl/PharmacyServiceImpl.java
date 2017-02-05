package com.upp.apteka.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Constants;
import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.repository.PharmacyRepository;
import com.upp.apteka.service.PharmacyService;

@Service("pharmacyService")
@Transactional
public class PharmacyServiceImpl implements PharmacyService{
	
	@Autowired
	PharmacyRepository pharmacyRepository;

	//get all pharmacies ordered by name
	public List<Pharmacy> getAllPharmacies(int offset) {
		
		return pharmacyRepository.getAll(offset, Constants.LIMIT);
	}

	public Pharmacy getPharmacy(Long idPharmacy) {
		
		return pharmacyRepository.read(idPharmacy);
	}

	public Long addPharmacy(Pharmacy pharmacy) {
		
		return pharmacyRepository.create(pharmacy);
	}

	public void updatePharmacy(Pharmacy pharmacy) {
		
		pharmacyRepository.update(pharmacy);		
	}

	public boolean deletePharmacy(Long idPharmacy) {
		
		return pharmacyRepository.delete(idPharmacy);
	}

	public List<Pharmacy> getPharmacyByName(String name) {
		
		return pharmacyRepository.findPharmacyByName(name);
	}

}
