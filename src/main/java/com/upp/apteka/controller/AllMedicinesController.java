package com.upp.apteka.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.Medicine;
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
		
		// get search query if any
		String query = (String) params.get("query");
		if (query == null)
			query = "";
		System.out.println("Query: " + query);

		// get current page
		System.out.println("CurrPage: " + params.get("сurrent"));
		System.out.println("PS: " + params.size());
		
		/**
		 * I'm LOCH
		 */
		String strPage = params.get("сurrent").toString();
		System.out.println("String page: " + strPage);
		int page = Integer.parseInt(strPage);
		//int page = (Integer) params.get("current");
		System.out.println("int page: " + page);
		
		System.out.println("we are here");

		// find medicines by query with offset and max
		List<Medicine> medicines = medicineService.findByQuery(query, (page - 1) * MEDICINES_PER_PAGE,
				MEDICINES_PER_PAGE, false);

		params.clear();

		// find patient by query result set size
		int maxNumber = medicineService.count(query, false);
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
		params.put("сurrent", page);
		params.put("medicines", medicines);
		params.put("query", query);

		allMedicinesActivity.showActivity(params);

	}

}
