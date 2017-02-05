package com.upp.apteka.repository.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Delivery;
import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.repository.MedicineRepository;
import com.upp.apteka.utils.repository.AHibernateRepository;

@Repository("medicineRepository")
public class MedicineRepositoryImpl extends AHibernateRepository<Medicine, Long> implements MedicineRepository {
	
	private static final Logger LOGGER = Logger.getLogger(MedicineRepositoryImpl.class.getName());

	@SuppressWarnings("unchecked")
	public List<Medicine> getAll(int offset, int limit) {

		return (List<Medicine>) createEntityCriteria().setFirstResult(offset).setMaxResults(limit).addOrder(Order.asc("name")).list();
	}

	public Long create(Medicine medicine) {

		return add(medicine);
	}

	public Medicine read(Long key) {

		return get(key);
	}

	public void update(Medicine medicine) {

		updateEntity(medicine);

	}

	public boolean delete(Long key) {

		return deleteEntity(key);
	}
	
	@SuppressWarnings("unchecked")
	public List<PharmacyMedicine> getPharmacyMedicines(Long id, int offset, int limit) {
		
		String hql = "SELECT phM"
				+ " FROM PharmacyMedicine phM"
				+ " WHERE phM.pharmacyMedicineID.pharmacy.id = :id AND phM.packQuantity != 0 order by phM.pharmacyMedicineID.medicine.name asc";
		
		Query query = createQuery(hql).setParameter("id", id);
		return  (List<PharmacyMedicine>) query.setFirstResult(offset).setMaxResults(limit).list();
	}

	public PharmacyMedicine getPharmacyMedicine(Long pharmacyId, Long medicineId) {
		String hql = "SELECT phM"
				+ " FROM PharmacyMedicine phM"
				+ " WHERE phM.pharmacyMedicineID.pharmacy.id = :pharmacyId AND phM.pharmacyMedicineID.medicine.id = :medicineId";
		
		Query query = createQuery(hql).setParameter("pharmacyId", pharmacyId).setParameter("medicineId", medicineId);
		return  (PharmacyMedicine) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<PharmacyMedicine> searchMedicineInPharmacies(Long medicineId, int offset, int limit) {
		String hql = "SELECT phM"
				+ " FROM PharmacyMedicine phM"
				+ " WHERE phM.pharmacyMedicineID.medicine.id = :medicineId AND phM.packQuantity != 0 order by phM.pharmacyMedicineID.pharmacy.name asc";
		
		Query query = createQuery(hql).setParameter("medicineId", medicineId);
		return  (List<PharmacyMedicine>) query.setFirstResult(offset).setMaxResults(limit).list();
	}

}
