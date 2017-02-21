package com.upp.apteka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.service.PrescriptionService;

@Component("viewPrescription")
public class ViewPrescriptionController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private PrescriptionService prescriptionService;

	public void switchToActivity(Map<String, Object> params) {
		Activity activity = (Activity) appContext.getBean("viewPrescriptionActivity");
		Long prescriptionId = (Long) params.get("id");

		Map<String, Object> newParams = new HashMap<String, Object>();
		newParams.put("prescription", prescriptionService.read(prescriptionId));
		activity.showActivity(newParams);

	}
}
