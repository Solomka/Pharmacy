package com.upp.apteka.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.Prescription;
import com.upp.apteka.service.PrescriptionService;

@Component("allPrescriptions")
public class AllPrescriptionsController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private PrescriptionService prescriptionService;

	private static final int PURCHASES_PER_PAGE = 20;

	@Override
	public void switchToActivity(Map<String, Object> params) {
		Activity allPrescriptionsActivity = (Activity) appContext.getBean("allPrescriptionsActivity");

		String query = (String) params.get("query");
		if (query == null)
			query = "";

		int page = (Integer) params.get("current");

		Date startDate = null;
		Date endDate = null;
		Boolean sold = (Boolean) params.get("sold");

		try {
			startDate = new Date(((java.util.Date) params.get("startDate")).getTime());
			endDate = new Date(((java.util.Date) params.get("endDate")).getTime());
		} catch(Exception e) {

		}

		List<Prescription> prescriptions = prescriptionService.findByQuery(query, startDate, endDate,
				PURCHASES_PER_PAGE * (page - 1), PURCHASES_PER_PAGE, false, sold);

		params.clear();

		int maxNumber = prescriptionService.count(query, startDate, endDate, false, sold);

		if (maxNumber % PURCHASES_PER_PAGE == 0  && maxNumber != 0)
			maxNumber = maxNumber / PURCHASES_PER_PAGE;
		else
			maxNumber = maxNumber / PURCHASES_PER_PAGE + 1;

		if (page > maxNumber)
			page = maxNumber;

		params.put("last", maxNumber);
		params.put("current", page);
		params.put("sold", sold);
		params.put("prescriptions", prescriptions);
		params.put("query", query);
		params.put("startDate", startDate);
		params.put("endDate", endDate);

		allPrescriptionsActivity.showActivity(params);

	}

}
