package com.upp.apteka.service;

import java.sql.Date;
import java.util.List;

import com.upp.apteka.bo.Delivery;

public interface DeliveryService {

	List<Delivery> getAllDeliveries(int offset);
	
	Delivery readDelivery(Long id);
	
	void addDelivery(Delivery delivery);
	
	void updateDelivery(Delivery delivery);
	
	boolean deleletDelivery(Long id);
	
	List<Delivery> findPharmacyDeliveriesByPeriod(Date from, Date to, Long pharmacyId, int offset);
	List<Delivery> findPharmacyMedicineDeliveriesByPeriod(Date from, Date to, Long pharmacyId, Long medicineId, int offset);
	
	

}
