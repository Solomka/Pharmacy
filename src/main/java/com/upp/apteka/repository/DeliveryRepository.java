package com.upp.apteka.repository;

import java.sql.Date;
import java.util.List;

import com.upp.apteka.bo.Delivery;
import com.upp.apteka.utils.repository.IRepository;

public interface DeliveryRepository extends IRepository<Delivery, Long> {

	List<Delivery> findPharmacyDeliveriesByPeriod(Date from, Date to, Long pharmacyId);
	List<Delivery> findPharmacyMedicineDeliveriesByPeriod(Date from, Date to, Long pharmacyId, Long medicineId);
	

}
