package com.upp.apteka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.service.PharmacyService;

@Component("addPharmacy")
public class AddPharmacyController implements SwingController {
	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private PharmacyService pharmacyService;

	public void switchToActivity(Map<String, Object> params) {
		Activity addPharmcyActivity = (Activity) appContext.getBean("addPharmacyActivity");

		Map<String, Object> newParams = new HashMap<String, Object>();

		Long pharmacyId = (Long) params.get("id");

		if (pharmacyId != null) {
			newParams.put("pharmacy", pharmacyService.getPharmacy(pharmacyId));
		}
		addPharmcyActivity.showActivity(newParams);

	}

}
