package com.upp.apteka.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.service.MedicineService;

@Component("viewPharmaciesMedicine")
public class ViewPharmaciesMedicineController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private MedicineService medicineService;

	private static final int MEDICINES_PER_PAGE = 20;

	public void switchToActivity(Map<String, Object> params) {

		Activity allMedicinesActivity = (Activity) appContext.getBean("viewPharmaciesMedicineActivity");

		// get search query if any
		String query = (String) params.get("query");

		if (query == null)
			query = "";

		int page = (Integer) params.get("current");

		// find medicines by query with offset and max
		List<PharmacyMedicine> medicines = medicineService.findPMByQuery(query, (page - 1) * MEDICINES_PER_PAGE,
				MEDICINES_PER_PAGE, false, false);

		// find medicine by query result set size
		int maxNumber = medicineService.countPM(query, false, false);
		System.out.println("result set size: " + maxNumber);

		/*
		 * establish pagination
		 */

		if (maxNumber % MEDICINES_PER_PAGE == 0 && maxNumber != 0)
			maxNumber = maxNumber / MEDICINES_PER_PAGE;
		else
			maxNumber = maxNumber / MEDICINES_PER_PAGE + 1;

		if (page > maxNumber)
			page = maxNumber;

		System.out.println("MaxNumber " + maxNumber);

		params.put("last", maxNumber);
		params.put("current", page);
		params.put("medicines", medicines);
		params.put("query", query);

		allMedicinesActivity.showActivity(params);

	}

}
