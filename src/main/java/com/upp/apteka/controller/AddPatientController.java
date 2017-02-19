package com.upp.apteka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.service.PatientService;

@Component("addPatient")
public class AddPatientController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private PatientService patientService;

	public void switchToActivity(Map<String, Object> params){
		Activity helloActivity = (Activity) appContext.getBean("addPatientActivity");
		
		Long patientId = (Long) params.get("id");
		
		if(patientId != null){
			Map<String, Object> newParams = new HashMap<String, Object>();
			newParams.put("patient", patientService.read(patientId));
			
			helloActivity.showActivity(newParams);
		}else
			helloActivity.showActivity(new HashMap<String, Object>());

	}
}
