package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.Prescription;
import com.upp.apteka.service.PrescriptionService;

@Component("addPurchase")
public class AddPurchaseController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private PrescriptionService prescriptionService;

	public void switchToActivity(Map<String, Object> params) {
		Activity activity = (Activity) appContext.getBean("addPurchaseActivity");

		Prescription prescription = prescriptionService.read((Long) params.get("prescriptionId"));
		params.clear();
		params.put("prescription", prescription);

		activity.showActivity(params);

	}
}
