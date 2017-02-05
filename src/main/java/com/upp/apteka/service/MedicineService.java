package com.upp.apteka.service;

import java.util.List;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.PharmacyMedicine;

public interface MedicineService {
	
	List<Medicine> getAllMedicines(int offset);
	
	Medicine getMedicine(Long medicineId);
	
	Long addMedicine(Medicine medicine);
	
	void updateMedicine(Medicine medicine);
	
	boolean deleteMedicine(Long medicineId);

	//get existing pharmacy medicines
	List<PharmacyMedicine> getPharmacyMedicines(Long id, int offset);

	List<PharmacyMedicine> getPharmacyMedicine(Long pharmacyId, String medicineName, int offset);

	List<PharmacyMedicine> searchMedicineInPharmacies(String medicineName, int offset);

}
