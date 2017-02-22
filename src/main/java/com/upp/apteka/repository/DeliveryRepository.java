package com.upp.apteka.repository;

import java.sql.Date;
import java.util.List;

import com.upp.apteka.bo.Delivery;
import com.upp.apteka.bo.DeliveryMedicine;
import com.upp.apteka.utils.repository.IRepository;

public interface DeliveryRepository extends IRepository<Delivery, Long> {

	List<Delivery> findPharmacyDeliveries(Long pharmacyId, int offset, int limit);

	List<Delivery> findPharmacyDeliveriesByPeriod(Date from, Date to, Long pharmacyId, int offset, int limit);

	List<Delivery> findPharmacyMedicineDeliveriesByPeriod(Date from, Date to, Long pharmacyId, Long medicineId,
			int offset, int limit);

	boolean checkIfDeliveryMedicineSold(Long deliveryId);

	List<DeliveryMedicine> getDeliveryMedicines(Long deliveryId, int offset, int limit);

	List<Delivery> findByQuery(String query, Date start, Date finish, int offset, int limit, boolean or);

	int countDM(Long deliveryId);

	int count(String query, Date start, Date finish, boolean or);

}
