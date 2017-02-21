package com.upp.apteka.service;

import java.awt.Container;
import java.util.List;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.validator.ValidationError;

import javassist.NotFoundException;

public interface MedicineService {

	List<Medicine> getAllMedicines(int offset, int limit);

	Medicine getMedicine(Long medicineId);

	Long addMedicine(Medicine medicine);

	void updateMedicine(Medicine medicine);

	boolean deleteMedicine(Long medicineId);

	// get existing pharmacy medicines
	List<PharmacyMedicine> getPharmacyMedicines(Long id, int offset);

	List<PharmacyMedicine> getPharmacyMedicine(Long pharmacyId, String medicineName, int offset);

	List<PharmacyMedicine> searchMedicineInPharmacies(String medicineName, int offset);

	List<ValidationError> processAdding(Container container) throws NotFoundException;

	List<ValidationError> processEditing(Container container, Long id) throws NotFoundException;
	
	boolean containsNameProducerMedicine(String name, String producer);
	
	int count();
	
	/*
	 * queries for general medicines
	 */
	
	List<Medicine> findByQuery(String query, boolean or);
	
	List<Medicine> findByQuery(String query, int offset, int limit, boolean or);
	
	int count(String query, boolean or);

}
