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

	@Override
	public void switchToActivity(Map<String, Object> params) {
		Activity addDoctorActivity = (Activity) appContext.getBean("allPatientsActivity");

		String query = (String) params.get("query");
		if (query == null)
			query = "";

		int page = (Integer) params.get("current");

		List<Patient> patients = patientService.findByQuery(query, (page - 1) * PATIENTS_PER_PAGE, PATIENTS_PER_PAGE,
				false);

		params.clear();

		int maxNumber = patientService.count(query, false);

		if (maxNumber % PATIENTS_PER_PAGE == 0)
			maxNumber = maxNumber / PATIENTS_PER_PAGE;
		else
			maxNumber = maxNumber / PATIENTS_PER_PAGE + 1;

		if (page > maxNumber)
			page = maxNumber;

		params.put("last", maxNumber);
		params.put("current", page);
		params.put("patients", patients);
		params.put("query", query);

		addDoctorActivity.showActivity(params);

	}

}
