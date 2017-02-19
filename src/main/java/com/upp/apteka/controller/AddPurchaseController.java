package com.upp.apteka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.Prescription;
import com.upp.apteka.service.PrescriptionService;
import com.upp.apteka.service.PurchaseService;

@Component("addPurchase")
public class AddPurchaseController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private PrescriptionService prescriptionService;

	@Autowired
	private PurchaseService purchaseService;

	public void switchToActivity(Map<String, Object> params) {
		Activity activity = (Activity) appContext.getBean("addPurchaseActivity");
		Long purchaseId = (Long) params.get("id");

		if (purchaseId != null) {
			Map<String, Object> newParams = new HashMap<String, Object>();
			newParams.put("purchase", purchaseService.read(purchaseId));
			newParams.put("prescription",  purchaseService.read(purchaseId).getPrescription());
			activity.showActivity(newParams);
		} else {
			Prescription prescription = prescriptionService.read((Long) params.get("prescriptionId"));
			params.clear();
			params.put("prescription", prescription);

			activity.showActivity(params);
		}

	}
}
