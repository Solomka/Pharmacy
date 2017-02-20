package com.upp.apteka.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.Patient;
import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.bo.Prescription;
import com.upp.apteka.bo.PrescriptionMedicine;
import com.upp.apteka.bo.Purchase;
import com.upp.apteka.bo.PurchaseMedicine;
import com.upp.apteka.dto.PurchaseMedicineDto;
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

		cleanUpBeforeCreate(purchase);
		return purchaseRepository.create(purchase);
	}

	private void cleanUpBeforeCreate(Purchase purchase) {
		List<PurchaseMedicine> purchMedicines = purchase.getPurchaseMedicines();
		Prescription prescription = purchase.getPrescription();

		List<PrescriptionMedicine> prescrMedicines = prescription.getPrescriptionMedicines();

		outer: for (PrescriptionMedicine prescrMedicine : prescrMedicines) {
			for (PurchaseMedicine purchMedicine : purchMedicines)
				if (prescrMedicine.getMedicine().getId() == purchMedicine.getMedicine().getId()) {
					prescrMedicine.setPackBought(prescrMedicine.getPackBought() + purchMedicine.getPackQuantity());

					if (prescrMedicine.getPackBought() > prescrMedicine.getPackQuantity())
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

					if (prescrMedicine.getPackBought() > prescrMedicine.getPackQuantity())
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
		cleanUpBeforeDelete(purchase);
		return purchaseRepository.delete(key);
	}

	private void cleanUpBeforeDelete(Purchase purchase) {
		List<PurchaseMedicine> purchaseMedicines = purchase.getPurchaseMedicines();
		Prescription prescription = purchase.getPrescription();

		List<PrescriptionMedicine> prescriptionMedicines = prescription.getPrescriptionMedicines();

		outer: for (PrescriptionMedicine prescriptionMedicine : prescriptionMedicines) {
			for (PurchaseMedicine purchaseMedicine : purchaseMedicines)
				if (prescriptionMedicine.getMedicine().getId() == purchaseMedicine.getMedicine().getId()) {
					prescriptionMedicine.setPackBought(prescriptionMedicine.getPackBought() - purchaseMedicine.getPackQuantity());

					Medicine medicine = prescriptionMedicine.getMedicine();
					List<PharmacyMedicine> pharmacyMedicines = medicine.getPharmacyMedicines();

					for (PharmacyMedicine pharmacyMedicine : pharmacyMedicines)
						if (pharmacyMedicine.getPharmacy().getId() == purchase.getPharmacy().getId())
							pharmacyMedicine.setPackQuantity(pharmacyMedicine.getPackQuantity() + purchaseMedicine.getPackQuantity());

					medicineService.updateMedicine(medicine);

					continue outer;
				}
		}

		prescriptionService.update(prescription);
		System.out.println(prescription.getPrescriptionMedicines());
	}

	//@Override
	public List<Purchase> findByPrescription(Long id) {
		return purchaseRepository.findByPrescription(id);
	}

	//@Override
	public Long create(Patient patient, Pharmacy pharmacy, Prescription prescription,
			List<PurchaseMedicineDto> purchaseMedicinesDto) {
		Purchase purchase = new Purchase();
		purchase.setDate(new Date(System.currentTimeMillis()));
		purchase.setPharmacy(pharmacy);
		purchase.setPrescription(prescription);

		Long id = purchaseRepository.create(purchase);
		purchase.setId(id);

		List<PurchaseMedicine> purchaseMedicines = new ArrayList<PurchaseMedicine>();

		for (PurchaseMedicineDto purchaseMedicineDto : purchaseMedicinesDto) {
			PurchaseMedicine purchaseMedicine = new PurchaseMedicine();
			purchaseMedicine.setMedicine(medicineService.getMedicine(purchaseMedicineDto.getMedicineId()));
			purchaseMedicine.setPackQuantity(purchaseMedicineDto.getQuantity());
			purchaseMedicine.setPurchase(purchase);

			purchaseMedicines.add(purchaseMedicine);

		}

		purchase.setPurchaseMedicines(purchaseMedicines);
		purchaseRepository.update(purchase);

		cleanUpBeforeCreate(purchase);
		
		return id;
	}

	//@Override
	public void update(Long id, Patient patient, Pharmacy pharmacy, Prescription prescription,
			List<PurchaseMedicineDto> purchaseMedicinesDto) {
		Purchase purchase = purchaseRepository.read(id);
		cleanUpBeforeDelete(purchase);
		
		purchase.setDate(new Date(System.currentTimeMillis()));
		purchase.setPharmacy(pharmacy);

		List<PurchaseMedicine> purchaseMedicines = purchase.getPurchaseMedicines();

		outer: for (PurchaseMedicineDto purchaseMedicineDto : purchaseMedicinesDto) {

			for (PurchaseMedicine purchaseMedicine : purchaseMedicines)
				if (purchaseMedicine.getMedicine().getId() == purchaseMedicineDto.getMedicineId()) {
					purchaseMedicine.setPackQuantity(purchaseMedicineDto.getQuantity());
					continue outer;
				}

			PurchaseMedicine missedPurchaseMedicine = new PurchaseMedicine();
			missedPurchaseMedicine.setMedicine(medicineService.getMedicine(purchaseMedicineDto.getMedicineId()));
			missedPurchaseMedicine.setPurchase(purchase);
			missedPurchaseMedicine.setPackQuantity(purchaseMedicineDto.getQuantity());

			purchaseMedicines.add(missedPurchaseMedicine);
		}

		outer: for (int i = 0; i < purchaseMedicines.size(); i++) {
			for (PurchaseMedicineDto purchaseMedicineDto : purchaseMedicinesDto)
				if (purchaseMedicineDto.getMedicineId() == purchaseMedicines.get(i).getMedicine().getId())
					continue outer;

			purchaseMedicines.remove(i);
			--i;
		}

		cleanUpBeforeCreate(purchase);
		purchaseRepository.update(purchase);
	}

	//@Override
	public int count(String query, Date start, Date finish, boolean or, Long number) {
		return purchaseRepository.count(query, start, finish, or, number);
	}


}
