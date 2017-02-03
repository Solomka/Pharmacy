package com.upp.apteka.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Delivery;
import com.upp.apteka.bo.DeliveryMedicine;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.repository.DeliveryRepository;
import com.upp.apteka.repository.PharmacyRepository;
import com.upp.apteka.service.DeliveryService;

@Service("deliveryService")
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	DeliveryRepository deliveryRepository;

	@Autowired
	PharmacyRepository pharmacyRepository;

	public static final int LIMIT = 5;

	public List<Delivery> getAllDeliveries(int offset) {
		return deliveryRepository.getAll(offset, LIMIT);
	}

	public Delivery readDelivery(Long id) {

		return deliveryRepository.read(id);
	}

	public void addDelivery(Delivery delivery) {

		// save new delivery
		Long delId = deliveryRepository.create(delivery);

		// get created delivery
		Delivery del = deliveryRepository.read(delId);

		// get delivery medicines
		List<DeliveryMedicine> delMeds = del.getDeliveryMedicines();

		// get all PharmacyMedicines by pharmacyI
		List<PharmacyMedicine> pharmMeds = pharmacyRepository.read(delivery.getPharmacy().getId())
				.getPharmacyMedicines();

		// new pharmacy medicines list
		List<DeliveryMedicine> newPharmacyMedicines = new ArrayList<DeliveryMedicine>();

		for (DeliveryMedicine delMed : delMeds) {
			if (pharmacyMedicineCheck(pharmMeds, delMed)) {

			} else {
				newPharmacyMedicines.add(delMed);
			}
		}

	}

	public void updateDelivery(Delivery delivery) {
		deliveryRepository.update(delivery);
	}

	public boolean deleletDelivery(Long id) {

		return deliveryRepository.delete(id);
	}

	private boolean pharmacyMedicineCheck(List<PharmacyMedicine> pharmMeds, DeliveryMedicine delMed) {

		for (PharmacyMedicine pharmMed : pharmMeds) {
			// if medicine exists in the pharmacy
			if (delMed.getMedicine().getId() == pharmMed.getMedicine().getId()) {
				return true;
			}
		}
		return false;
	}

	// Delivery repo specific requests

	public List<Delivery> findPharmacyDeliveriesByPeriod(Date from, Date to, Long pharmacyId, int offset) {
		return deliveryRepository.findPharmacyDeliveriesByPeriod(from, to, pharmacyId, offset, LIMIT);
	}

	public List<Delivery> findPharmacyMedicineDeliveriesByPeriod(Date from, Date to, Long pharmacyId, Long medicineId,
			int offset) {
		return deliveryRepository.findPharmacyMedicineDeliveriesByPeriod(from, to, pharmacyId, medicineId, offset,
				LIMIT);
	}

}
