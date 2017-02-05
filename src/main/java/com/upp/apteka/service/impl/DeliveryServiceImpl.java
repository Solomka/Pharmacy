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
import com.upp.apteka.service.DeliveryService;
import com.upp.apteka.service.PharmacyService;

@Service("deliveryService")
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	DeliveryRepository deliveryRepository;

	@Autowired
	PharmacyService pharmacyService;

	public List<Delivery> getAllDeliveries(int offset) {

		return deliveryRepository.getAll(offset, Constants.LIMIT);
	}

	public List<Delivery> getAllPharmacyDeliveries(Long pharmacyId, int offset) {

		return deliveryRepository.findPharmacyDeliveries(pharmacyId, offset, Constants.LIMIT);
	}

	public Delivery getDelivery(Long id) {

		return deliveryRepository.read(id);
	}

	public void addDelivery(Delivery delivery) {

		// save new delivery
		Long delId = deliveryRepository.create(delivery);

		// get delivery medicines
		List<DeliveryMedicine> delMeds = delivery.getDeliveryMedicines();

		// get delivery pharmacy
		Pharmacy pharmacy = pharmacyService.getPharmacy(delivery.getPharmacy().getId());

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

		pharmacyService.updatePharmacy(pharmacy);

	}

	public void updateDelivery(Delivery delivery) {

		deliveryRepository.update(delivery);
	}

	public boolean deleteDelivery(Long id) {
		
		if(deliveryRepository.checkIfDeliveryMedicineSold(id)){
			return false;
		}else{
			//update pharmacy medicines quntity
			Delivery delivery = deliveryRepository.read(id);
			
			// get delivery pharmacy
			Pharmacy pharmacy = pharmacyService.getPharmacy(delivery.getPharmacy().getId());

			// get all PharmacyMedicines by pharmacyId
			List<PharmacyMedicine> pharmMeds = pharmacy.getPharmacyMedicines();

			List<DeliveryMedicine> delMeds = delivery.getDeliveryMedicines();
			
			for (DeliveryMedicine delMed : delMeds) {
				if (pharmacyMedicineCheck(pharmMeds, delMed)) {

					for (PharmacyMedicine pharmMed : pharmMeds) {
						if (pharmMed.getMedicine().getId().equals(delMed.getMedicine().getId())) {
							pharmMed.setPackQuantity(pharmMed.getPackQuantity() - generatePMPackQuantity(delMed));
						}
					}

				} 
				}	
			pharmacyService.updatePharmacy(pharmacy);
			return deliveryRepository.delete(id);
		}
	}

	// Delivery repo specific requests

	public List<Delivery> getPharmacyDeliveriesByPeriod(Date from, Date to, Long pharmacyId, int offset) {
		return deliveryRepository.findPharmacyDeliveriesByPeriod(from, to, pharmacyId, offset, Constants.LIMIT);
	}

	public List<Delivery> getPharmacyMedicineDeliveriesByPeriod(Date from, Date to, Long pharmacyId, Long medicineId,
			int offset) {
		return deliveryRepository.findPharmacyMedicineDeliveriesByPeriod(from, to, pharmacyId, medicineId, offset,
				Constants.LIMIT);
	}
	
	public boolean checkIfDeliveryMedicineSold(Long deliveryId){
		
		return false;
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
