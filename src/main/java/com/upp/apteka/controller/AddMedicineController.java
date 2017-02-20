package com.upp.apteka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.service.MedicineService;

@Component("addMedicine")
public class AddMedicineController implements SwingController {

	@Autowired
	ApplicationContext appContext;

	@Autowired
	MedicineService medicineService;

	public void switchToActivity(Map<String, Object> params) {

		Activity addMedicineActivity = (Activity) appContext.getBean("addMedicineActivity");

		Map<String, Object> newParams = new HashMap<String, Object>();

		Long medicineId = (Long) params.get("id");

		if (medicineId != null) {
			newParams.put("medicine", medicineService.getMedicine(medicineId));
		}

		addMedicineActivity.showActivity(newParams);

	}

}
