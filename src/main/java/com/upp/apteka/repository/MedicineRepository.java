package com.upp.apteka.repository;

import java.util.List;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.utils.repository.IRepository;

public interface MedicineRepository extends IRepository<Medicine, Long> {
	
	List<PharmacyMedicine> getPharmacyMedicines(Long id, int offset, int limit);
	
	List<PharmacyMedicine> getPharmacyMedicine(Long pharmacyId, String medicineName, int offset, int limit);
	
	List<PharmacyMedicine> searchMedicineInPharmacies(String medicineName, int offset, int limit);

	List<Medicine> findByNameOrProducer(String query);
	
	boolean containsNameProducerMedicine(String name, String producer);

	public int count();
}
