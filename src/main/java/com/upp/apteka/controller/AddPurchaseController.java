package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.AddPurchaseActivity;
import com.upp.apteka.service.PharmacyService;
import com.upp.apteka.service.PrescriptionService;

@Component("addPurchase")
public class AddPurchaseController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private PrescriptionService prescriptionService;

	@Autowired
	private PharmacyService pharmacyService;

	public void switchToActivity(Map<String, Object> params) {
		AddPurchaseActivity activity = appContext.getBean(AddPurchaseActivity.class);
		activity.showActivity(prescriptionService.read((Long) params.get("prescriptionId")),
				pharmacyService.getPharmacy((Long) params.get("pharmacyId")));

	}
}
