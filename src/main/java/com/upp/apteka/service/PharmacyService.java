package com.upp.apteka.service;

import java.awt.Container;
import java.util.List;

import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.validator.ValidationError;

import javassist.NotFoundException;

public interface PharmacyService {
	
	List<Pharmacy> getAll();

	List<Pharmacy> getAllPharmacies(int offset);

	Pharmacy getPharmacy(Long idPharmacy);

	Long addPharmacy(Pharmacy pharmacy);

	void updatePharmacy(Pharmacy pharmacy);

	boolean deletePharmacy(Long idPharmacy);

	List<Pharmacy> getPharmacyByName(String name);

	// if exists pharamacy with such name
	boolean containsAddress(String address);

	List<ValidationError> processAdding(Container container) throws NotFoundException;

	List<ValidationError> processEditing(Container container, Long id) throws NotFoundException;
	
	List<Pharmacy> findByQuery(String query, boolean or);

	List<Pharmacy> findByQuery(String query, int offset, int limit, boolean or);

	int count(String query, boolean or);

}
