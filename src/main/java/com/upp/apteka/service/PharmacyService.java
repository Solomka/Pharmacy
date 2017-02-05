package com.upp.apteka.service;

import java.util.List;

import com.upp.apteka.bo.Pharmacy;

public interface PharmacyService {
	
	List<Pharmacy> getAllPharmacies(int offset);
	
	Pharmacy getPharmacy(Long idPharmacy);
	
	Long addPharmacy(Pharmacy pharmacy);
	
	void updatePharmacy(Pharmacy pharmacy);
	
	boolean deletePharmacy(Long idPharmacy);
	
	List<Pharmacy> findPharmacyByName(String name);
	

}
