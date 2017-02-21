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
	
	/*
	 * queries for general medicines
	 */
	
	List<Medicine> findByQuery(String query, boolean or);
	
	List<Medicine> findByQuery(String query, int offset, int limit, boolean or);
	
	int count(String query, boolean or);
	
	/*
	 * queries for pharmacyMedicines
	 */
	
	List<PharmacyMedicine>findPMByQuery(String query, int offset, int limit, boolean or);
	
	public int countPM(String query, boolean or);
	
}
