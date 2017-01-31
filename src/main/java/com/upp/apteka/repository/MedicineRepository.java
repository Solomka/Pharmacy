package com.upp.apteka.repository;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.utils.repository.HqlSpecification;
import com.upp.apteka.utils.repository.IRepository;

public interface MedicineRepository extends IRepository<Medicine, Long, HqlSpecification> {

}
