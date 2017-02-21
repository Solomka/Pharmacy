package com.upp.apteka.service.impl;

import java.awt.Container;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Constants;
import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.dto.MedicineDto;
import com.upp.apteka.repository.MedicineRepository;
import com.upp.apteka.service.MedicineService;
import com.upp.apteka.validator.MedicineValidator;
import com.upp.apteka.validator.ValidationError;

import javassist.NotFoundException;

@Service("medicineService")
@Transactional
public class MedicineServiceImpl implements MedicineService {

	@Autowired
	ApplicationContext appContext;

	@Autowired
	MedicineRepository medicineRepository;

	public List<Medicine> getAllMedicines(int offset, int limit) {

		return medicineRepository.getAll(offset, limit);
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
	
public boolean containsNameProducerMedicine(String name, String producer) {
		
		return medicineRepository.containsNameProducerMedicine(name, producer);
	}


	public List<ValidationError> processAdding(Container container) throws NotFoundException {
		MedicineDto medicineDto = appContext.getBean(MedicineDto.class);
		medicineDto.readFromContext(container);

		MedicineValidator medicineValidator = appContext.getBean(MedicineValidator.class);

		List<ValidationError> errors = medicineValidator.validate(medicineDto);		
		
		medicineDto.showErrors(errors, container);

		if (errors.isEmpty()) {
			System.err.println("No errors");
			addMedicine(new Medicine(medicineDto));
		}

		return errors;
	}

	public List<ValidationError> processEditing(Container container, Long id) throws NotFoundException {
		MedicineDto medicineDto = appContext.getBean(MedicineDto.class);
		medicineDto.readFromContext(container);

		MedicineValidator medicineValidator = appContext.getBean(MedicineValidator.class);

		List<ValidationError> errors = medicineValidator.validate(medicineDto);
		medicineDto.showErrors(errors, container);

		Medicine medicine = new Medicine(medicineDto);
		medicine.setId(id);

		if (errors.isEmpty())
			updateMedicine(medicine);

		return errors;
	}

	public int count() {
		// TODO Auto-generated method stub
		return medicineRepository.count();
	}

	
}
