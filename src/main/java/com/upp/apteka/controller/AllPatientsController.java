package com.upp.apteka.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.Patient;
import com.upp.apteka.service.PatientService;

@Component("allPatients")
public class AllPatientsController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private PatientService patientService;

	private static final int PATIENTS_PER_PAGE = 20;

	//@Override
	public void switchToActivity(Map<String, Object> params) {
		Activity addDoctorActivity = (Activity) appContext.getBean("allPatientsActivity");

		//get search query if any
		String query = (String) params.get("query");		
		if (query == null)
			query = "";
		System.out.println("Query: " + query);

		//get current page
		int page = (Integer) params.get("current");
		System.out.println("Current patient page: " + page);

		//find patients by query with offset and max
		List<Patient> patients = patientService.findByQuery(query, (page - 1) * PATIENTS_PER_PAGE, PATIENTS_PER_PAGE,
				false);

		params.clear();

		//find patient by query result set size
		int maxNumber = patientService.count(query, false);
		System.out.println("result set size: " + maxNumber);

		/*
		 * establish pagination
		 */
		if (maxNumber % PATIENTS_PER_PAGE == 0 && maxNumber != 0)
			maxNumber = maxNumber / PATIENTS_PER_PAGE;
		else
			maxNumber = maxNumber / PATIENTS_PER_PAGE + 1;

		if (page > maxNumber)
			page = maxNumber;
		
		System.out.println("MaxNumber " + maxNumber);

		params.put("last", maxNumber);
		params.put("current", page);
		params.put("patients", patients);
		params.put("query", query);

		addDoctorActivity.showActivity(params);

	}

}
