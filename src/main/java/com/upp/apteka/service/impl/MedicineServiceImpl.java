package com.upp.apteka.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Constants;
import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.repository.MedicineRepository;
import com.upp.apteka.service.MedicineService;

@Service("medicineService")
@Transactional
public class MedicineServiceImpl implements MedicineService {

	@Autowired
	MedicineRepository medicineRepository;

	public List<Medicine> getAllMedicines(int offset) {

		return medicineRepository.getAll(offset, Constants.LIMIT);
	}

	public Medicine getMedicine(Long medicineId) {

		return medicineRepository.read(medicineId);
	}

	public Long addMedicine(Medicine medicine) {

		return medicineRepository.create(medicine);
	}

	public void updateMedicine(Medicine medicine) {

		medicineRepository.update(medicine);
	}

	public boolean deleteMedicine(Long medicineId) {

		return medicineRepository.delete(medicineId);
	}

	public List<PharmacyMedicine> getPharmacyMedicines(Long id, int offset) {

		return medicineRepository.getPharmacyMedicines(id, offset, Constants.LIMIT);
	}

	public List<PharmacyMedicine> getPharmacyMedicine(Long pharmacyId, String medicineName, int offset) {

		return medicineRepository.getPharmacyMedicine(pharmacyId, medicineName, offset, Constants.LIMIT);
	}

	public List<PharmacyMedicine> searchMedicineInPharmacies(String medicineName, int offset) {

		return medicineRepository.searchMedicineInPharmacies(medicineName, offset, Constants.LIMIT);
	}

}
