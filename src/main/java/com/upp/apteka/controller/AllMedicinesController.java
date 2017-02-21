package com.upp.apteka.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.Patient;
import com.upp.apteka.service.MedicineService;

@Component("allMedicines")
public class AllMedicinesController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private MedicineService medicineService;

	private static final int MEDICINES_PER_PAGE = 20;

	public void switchToActivity(Map<String, Object> params) {
		Activity allMedicinesActivity = (Activity) appContext.getBean("allMedicinesActivity");

		String query = (String) params.get("query");
		if (query == null)
			query = "";

		int page = (Integer) params.get("сurrent");
		System.out.println("CurrPage: " + page);

		List<Medicine> medicines = medicineService.getAllMedicines((page -1 ) * MEDICINES_PER_PAGE , MEDICINES_PER_PAGE  );			

		params.clear();

		int maxNumber = medicineService.count();

		if (maxNumber % MEDICINES_PER_PAGE == 0 && maxNumber != 0)
			maxNumber = maxNumber / MEDICINES_PER_PAGE;
		else
			maxNumber = maxNumber / MEDICINES_PER_PAGE + 1;

		if (page > maxNumber)
			page = maxNumber;

		params.put("last", maxNumber);
		params.put("current", page);
		params.put("medicines", medicines);
		params.put("query", query);

		allMedicinesActivity.showActivity(params);

	}

}
