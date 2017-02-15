package com.upp.apteka.repository;

import java.util.List;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.utils.repository.IRepository;

public interface MedicineRepository extends IRepository<Medicine, Long> {

	List<Medicine> findByNameOrProducer(String query);

}
