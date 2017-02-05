package com.upp.apteka.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Constants;
import com.upp.apteka.bo.Delivery;
import com.upp.apteka.bo.DeliveryMedicine;
import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.Pharmacy;
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

	public List<Delivery> getAllDeliveries(int offset) {
		
		return deliveryRepository.getAll(offset, Constants.LIMIT);
	}

	public Delivery readDelivery(Long id) {
		
		return deliveryRepository.read(id);
	}

	public void addDelivery(Delivery delivery) {

		// save new delivery
		Long delId = deliveryRepository.create(delivery);

		// get delivery medicines
		List<DeliveryMedicine> delMeds = delivery.getDeliveryMedicines();

		// get delivery pharmacy
		Pharmacy pharmacy = pharmacyRepository.read(delivery.getPharmacy().getId());

		// get all PharmacyMedicines by pharmacyId
		List<PharmacyMedicine> pharmMeds = pharmacy.getPharmacyMedicines();

		PharmacyMedicine pharmacyMedicine;

		for (DeliveryMedicine delMed : delMeds) {
			if (pharmacyMedicineCheck(pharmMeds, delMed)) {

				for (PharmacyMedicine pharmMed : pharmMeds) {
					if (pharmMed.getMedicine().getId().equals(delMed.getMedicine().getId())) {
						pharmMed.setPackQuantity(pharmMed.getPackQuantity() + generatePMPackQuantity(delMed));
					}
				}

			} else {
				// create new pharmacy medicine
				pharmacyMedicine = new PharmacyMedicine();
				pharmacyMedicine.setMedicine(delMed.getMedicine());
				pharmacyMedicine.setPharmacy(delivery.getPharmacy());
				pharmacyMedicine.setPackQuantity(generatePMPackQuantity(delMed));
				pharmacyMedicine.setPackPrice(generatePMPackPrice(delivery.getPharmacy(), delMed.getMedicine()));

				pharmMeds.add(pharmacyMedicine);
			}
		}

		pharmacyRepository.update(pharmacy);

	}
	
	public void updateDelivery(Delivery delivery) {
		
		deliveryRepository.update(delivery);
	}

	public boolean deleteDelivery(Long id) {
		/*
		 * Delivery delivey = deliveryRepository.read(id); Pharmacy pharmacy =
		 * pharmacyRepository.read(delivey.getPharmacy().getId());
		 * 
		 * List<PharmacyMedicine> pharmMeds = pharmacy.getPharmacyMedicines();
		 * 
		 * // get delivery medicines List<DeliveryMedicine> delMeds =
		 * delivey.getDeliveryMedicines();
		 * 
		 * for (DeliveryMedicine delMed : delMeds) { if
		 * (pharmacyMedicineCheck(pharmMeds, delMed)) {
		 * 
		 * for (PharmacyMedicine pharmMed : pharmMeds) { if
		 * (pharmMed.getMedicine().getId() == delMed.getMedicine().getId()) {
		 * 
		 * if(pharmMed.getPackQuantity() -
		 * delMed.getBoxQuantity()*delMed.getMedicine().getQuantityPerBox()
		 * >=0){ pharmMed.setPackQuantity(pharmMed.getPackQuantity() -
		 * delMed.getBoxQuantity()*delMed.getMedicine().getQuantityPerBox()); }
		 * } }
		 * 
		 * } else { // create new pharmacy medicine pharmacyMedicine = new
		 * PharmacyMedicine();
		 * pharmacyMedicine.setMedicine(delMed.getMedicine());
		 * pharmacyMedicine.setPharmacy(delivery.getPharmacy());
		 * pharmacyMedicine.setPackQuantity(generatePMPackQuantity(delMed));
		 * pharmacyMedicine.setPackPrice(generatePMPackPrice(delivery.
		 * getPharmacy(), delMed.getMedicine()));
		 * 
		 * pharmMeds.add(pharmacyMedicine); } }
		 * 
		 */

		return deliveryRepository.delete(id);

	}

	// Delivery repo specific requests

		public List<Delivery> findPharmacyDeliveriesByPeriod(Date from, Date to, Long pharmacyId, int offset) {
			return deliveryRepository.findPharmacyDeliveriesByPeriod(from, to, pharmacyId, offset, Constants.LIMIT);
		}

		public List<Delivery> findPharmacyMedicineDeliveriesByPeriod(Date from, Date to, Long pharmacyId, Long medicineId,
				int offset) {
			return deliveryRepository.findPharmacyMedicineDeliveriesByPeriod(from, to, pharmacyId, medicineId, offset,
					Constants.LIMIT);
		}
		
	private int generatePMPackQuantity(DeliveryMedicine delMed) {

		return delMed.getBoxQuantity() * delMed.getMedicine().getQuantityPerBox();

	}

	private BigDecimal generatePMPackPrice(Pharmacy pharmacy, Medicine medicine) {

		BigDecimal hundred = new BigDecimal(100);

		BigDecimal vat = new BigDecimal(Constants.VAT).divide(hundred, MathContext.DECIMAL64);
		BigDecimal pharmacyExtra = new BigDecimal(pharmacy.getExtra()).divide(hundred, MathContext.DECIMAL64);

		BigDecimal purePricePerPack = medicine.getBoxPrice().divide(new BigDecimal(medicine.getQuantityPerBox()),
				MathContext.DECIMAL64);

		BigDecimal pricePerPackWithExtra = purePricePerPack
				.add(purePricePerPack.multiply(pharmacyExtra, MathContext.DECIMAL64));

		BigDecimal pricePerPackWithVAT = pricePerPackWithExtra.add(pricePerPackWithExtra).multiply(vat,
				MathContext.DECIMAL64);

		return pricePerPackWithVAT;
	}	

	private boolean pharmacyMedicineCheck(List<PharmacyMedicine> pharmMeds, DeliveryMedicine delMed) {

		for (PharmacyMedicine pharmMed : pharmMeds) {
			// if medicine exists in the pharmacy

			System.out.println("delMed id: " + delMed.getMedicine().getId());
			System.out.println("pharmMed id: " + pharmMed.getMedicine().getId());
			if (delMed.getMedicine().getId().equals(pharmMed.getMedicine().getId())) {
				return true;
			}
		}
		return false;
	}

	

}
