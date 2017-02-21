package com.upp.apteka.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.service.PharmacyService;

@Component("allPharmacies")
public class AllPharmaciesController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private PharmacyService pharmacyService;

	private static final int PHARMACIES_PER_PAGE = 20;

	public void switchToActivity(Map<String, Object> params) {
		Activity allPharmaciesActivity = (Activity) appContext.getBean("allPharmaciesActivity");

		/**
		 * I'm LOCH
		 */
		System.out.println("String page: " + params.get("сurrent").toString());
		String strPage = params.get("сurrent").toString();		
		int page = Integer.parseInt(strPage);
		//int page = (Integer) params.get("current");
		System.out.println("int page: " + page);
		
		String query = (String) params.get("query");
		if (query == null)
			query = "";		

		List<Pharmacy> pharmacies = pharmacyService.findByQuery(query, (page - 1) * PHARMACIES_PER_PAGE,
				PHARMACIES_PER_PAGE, false);

		params.clear();

		int maxNumber = pharmacyService.count(query, false);

		if (maxNumber % PHARMACIES_PER_PAGE == 0 && maxNumber != 0)
			maxNumber = maxNumber / PHARMACIES_PER_PAGE;
		else
			maxNumber = maxNumber / PHARMACIES_PER_PAGE + 1;

		if (page > maxNumber)
			page = maxNumber;

		params.put("last", maxNumber);
		params.put("current", page);
		params.put("pharmacies", pharmacies);
		params.put("query", query);

		allPharmaciesActivity.showActivity(params);

	}

}
