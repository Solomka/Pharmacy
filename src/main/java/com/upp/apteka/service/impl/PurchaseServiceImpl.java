package com.upp.apteka.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.bo.Prescription;
import com.upp.apteka.bo.PrescriptionMedicine;
import com.upp.apteka.bo.Purchase;
import com.upp.apteka.bo.PurchaseMedicine;
import com.upp.apteka.repository.PurchaseRepository;
import com.upp.apteka.service.MedicineService;
import com.upp.apteka.service.PrescriptionService;
import com.upp.apteka.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseRepository purchaseRepository;

	@Autowired
	private PrescriptionService prescriptionService;

	@Autowired
	private MedicineService medicineService;

	// @Override
	public List<Purchase> getAll(int offset, int limit) {
		return purchaseRepository.getAll(offset, limit);
	}

	// @Override
	public List<Purchase> findByQuery(String query, Date start, Date finish, boolean or, Long number) {
		return purchaseRepository.findByQuery(query, start, finish, or, number);
	}

	// @Override
	public List<Purchase> findByQuery(String query, Date start, Date finishDate, int offset, int limit, boolean or,
			Long number) {
		return purchaseRepository.findByQuery(query, start, finishDate, offset, limit, or, number);
	}

	// @Override
	@Transactional
	public Long create(Purchase purchase) {
		List<PurchaseMedicine> purchMedicines = purchase.getPurchaseMedicines();
		Prescription prescription = purchase.getPrescription();

		List<PrescriptionMedicine> prescrMedicines = prescription.getPrescriptionMedicines();

		outer: for (PrescriptionMedicine prescrMedicine : prescrMedicines) {
			for (PurchaseMedicine purchMedicine : purchMedicines)
				if (prescrMedicine.getMedicine().getId() == purchMedicine.getMedicine().getId()) {
					prescrMedicine.setPackBought(prescrMedicine.getPackBought() + purchMedicine.getPackQuantity());

					if (prescrMedicine.getPackBought() + purchMedicine.getPackQuantity() > prescrMedicine
							.getPackQuantity())
						throw new IllegalArgumentException("Чітер. Занадто багато лікарств. Наркоман, напевно!");

					List<PharmacyMedicine> pharmacyMedicines = prescrMedicine.getMedicine().getPharmacyMedicines();

					for (PharmacyMedicine pm : pharmacyMedicines)
						if (pm.getPharmacy().getId() == purchase.getPharmacy().getId()) {
							if (pm.getPackQuantity() < purchMedicine.getPackQuantity())
								throw new IllegalArgumentException(
										"В реальному житті я би хотів так продавати неіснуючі товари, але зараз це баг.");

							pm.setPackQuantity(pm.getPackQuantity() - purchMedicine.getPackQuantity());
						}

					medicineService.updateMedicine(prescrMedicine.getMedicine());
					continue outer;
				}
		}

		prescriptionService.update(prescription);

		return purchaseRepository.create(purchase);
	}

	// @Override
	public Purchase read(Long key) {
		return purchaseRepository.read(key);
	}

	// @Override
	@Transactional
	public void update(Purchase newPurchase) {
		Purchase purchase = read(newPurchase.getId());
		List<PurchaseMedicine> purchMedicines = purchase.getPurchaseMedicines();
		Prescription prescription = purchase.getPrescription();

		List<PrescriptionMedicine> prescrMedicines = prescription.getPrescriptionMedicines();

		outer: for (PrescriptionMedicine prescrMedicine : prescrMedicines) {
			for (PurchaseMedicine purchMedicine : purchMedicines)
				if (prescrMedicine.getMedicine().getId() == purchMedicine.getMedicine().getId()) {
					prescrMedicine.setPackBought(prescrMedicine.getPackBought() - purchMedicine.getPackQuantity());

					List<PharmacyMedicine> pharmacyMedicines = prescrMedicine.getMedicine().getPharmacyMedicines();

					for (PharmacyMedicine pm : pharmacyMedicines)
						if (pm.getPharmacy().getId() == purchase.getPharmacy().getId())
							pm.setPackQuantity(pm.getPackQuantity() + purchMedicine.getPackQuantity());

					medicineService.updateMedicine(prescrMedicine.getMedicine());

					continue outer;
				}
		}

		purchMedicines = newPurchase.getPurchaseMedicines();
		prescription = newPurchase.getPrescription();

		prescrMedicines = prescription.getPrescriptionMedicines();

		outer: for (PrescriptionMedicine prescrMedicine : prescrMedicines) {
			for (PurchaseMedicine purchMedicine : purchMedicines)
				if (prescrMedicine.getMedicine().getId() == purchMedicine.getMedicine().getId()) {
					prescrMedicine.setPackBought(prescrMedicine.getPackBought() + purchMedicine.getPackQuantity());

					if (prescrMedicine.getPackBought() + purchMedicine.getPackQuantity() > prescrMedicine
							.getPackQuantity())
						throw new IllegalArgumentException("Чітер. Занадто багато лікарств. Наркоман, напевно!");

					List<PharmacyMedicine> pharmacyMedicines = prescrMedicine.getMedicine().getPharmacyMedicines();

					for (PharmacyMedicine pm : pharmacyMedicines)
						if (pm.getPharmacy().getId() == purchase.getPharmacy().getId()) {
							if (pm.getPackQuantity() < purchMedicine.getPackQuantity())
								throw new IllegalArgumentException(
										"В реальному житті я би хотів так продавати неіснуючі товари, але зараз це баг.");

							pm.setPackQuantity(pm.getPackQuantity() - purchMedicine.getPackQuantity());
						}

					medicineService.updateMedicine(prescrMedicine.getMedicine());

					continue outer;
				}
		}
		prescriptionService.update(prescription);
		purchaseRepository.update(purchase);

	}

	// @Override
	@Transactional
	public boolean delete(Long key) {
		Purchase purchase = read(key);
		List<PurchaseMedicine> purchMedicines = purchase.getPurchaseMedicines();
		Prescription prescription = purchase.getPrescription();

		List<PrescriptionMedicine> prescrMedicines = prescription.getPrescriptionMedicines();

		outer: for (PrescriptionMedicine prescrMedicine : prescrMedicines) {
			for (PurchaseMedicine purchMedicine : purchMedicines)
				if (prescrMedicine.getMedicine().getId() == purchMedicine.getMedicine().getId()) {
					prescrMedicine.setPackBought(prescrMedicine.getPackBought() - purchMedicine.getPackQuantity());

					List<PharmacyMedicine> pharmacyMedicines = prescrMedicine.getMedicine().getPharmacyMedicines();

					for (PharmacyMedicine pm : pharmacyMedicines)
						if (pm.getPharmacy().getId() == purchase.getPharmacy().getId())
							pm.setPackQuantity(pm.getPackQuantity() + purchMedicine.getPackQuantity());

					medicineService.updateMedicine(prescrMedicine.getMedicine());

					continue outer;
				}
		}

		prescriptionService.update(prescription);
		return purchaseRepository.delete(key);
	}

	@Override
	public List<Purchase> findByPrescription(Long id) {
		return purchaseRepository.findByPrescription(id);
	}

}
